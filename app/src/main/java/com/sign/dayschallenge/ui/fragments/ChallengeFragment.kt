package com.sign.dayschallenge.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sign.dayschallenge.R
import com.sign.dayschallenge.adapters.ChallengeItemDecorator
import com.sign.dayschallenge.adapters.DayAdapter
import com.sign.dayschallenge.data.DayState
import com.sign.dayschallenge.databinding.ChallengeFragmentLayoutBinding
import com.sign.dayschallenge.ui.MainActivity
import com.sign.dayschallenge.utils.ActionResponse
import com.sign.dayschallenge.utils.DialogUtil

class ChallengeFragment : Fragment() {

    private val args : ChallengeFragmentArgs by navArgs()
    private var _binding: ChallengeFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var dayAdapter: DayAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivChallengeIcon.setImageDrawable(context?.getDrawable(args.argChallenge.imgResource))
        binding.tvDescription.text = args.argChallenge.description
        binding.tvTitle.text = args.argChallenge.title

        initRecyclerView()

        dayAdapter.itemCLickListener = { dayState, position->
            when(dayState){
                DayState.EMPTY.ordinal, DayState.SKIP_DAY.ordinal ->{
                    Toast.makeText(requireContext(), "You cant change state here", Toast.LENGTH_SHORT).show()
                }
                DayState.TODAY.ordinal->{
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage("Do you want to complete day?")
                        .setTitle("Change State")
                        .setNegativeButton("No") { _, _ -> }
                        .setPositiveButton("Yes") { dialog, which ->
                            if (activity!=null){
                                val result = (activity as MainActivity).viewModelApp.updateOneItem(args.argChallenge, 1, position)
                                (activity as MainActivity).viewModelApp.updateChallenge(result)
                                dayAdapter.differAsync.submitList(result.daysInMillis?.zip(result.daysState))
                            }
                        }.show()
                }
            }



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChallengeFragmentLayoutBinding.inflate(inflater, container, false)
        binding.ivBack.setOnClickListener {
            Log.e("asds","sadsa")
            (activity as MainActivity).onBackPressed()
        }

        binding.ivEdit.setOnClickListener {
            val challenge = args.argChallenge
            val action = ChallengeFragmentDirections.actionChallengeFragmentToEditChallengeFragment(challenge)
            findNavController().navigate(action)
        }
        return binding.root
    }

    private fun initRecyclerView(){
        val space = resources.getDimensionPixelSize(R.dimen.rec_view)
        dayAdapter = DayAdapter(requireContext())
        dayAdapter.differAsync.submitList(args.argChallenge.daysInMillis?.zip(args.argChallenge.daysState))
        binding.rvContent.apply{
            adapter = dayAdapter
            layoutManager = object : GridLayoutManager(requireContext(), 5, VERTICAL, false){
                override fun checkLayoutParams(lp: RecyclerView.LayoutParams): Boolean {
                    val currentWidth = width-(space*6)
                    lp.width = (currentWidth/5)
                    lp.height = (currentWidth/5)
                    return true
                }
            }
            addItemDecoration(ChallengeItemDecorator(space))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}