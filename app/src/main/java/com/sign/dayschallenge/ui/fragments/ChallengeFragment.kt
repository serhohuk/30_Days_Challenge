package com.sign.dayschallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sign.dayschallenge.R
import com.sign.dayschallenge.adapters.ChallengeItemDecorator
import com.sign.dayschallenge.adapters.DayAdapter
import com.sign.dayschallenge.databinding.ChallengeFragmentLayoutBinding

class ChallengeFragment : Fragment() {

    private val args : ChallengeFragmentArgs by navArgs()
    private var _binding: ChallengeFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var dayAdapter: DayAdapter



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivChallengeIcon.setImageDrawable(context?.getDrawable(args.argChallenge.imgResource))
        binding.tvDescription.text = args.argChallenge.description

        initRecyclerView()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChallengeFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun initRecyclerView(){
        val space = resources.getDimensionPixelSize(R.dimen.rec_view)
        dayAdapter = DayAdapter(requireContext())
        dayAdapter.differAsync.submitList(args.argChallenge.daysState)
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