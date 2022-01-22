package com.sign.dayschallenge.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sign.dayschallenge.R
import com.sign.dayschallenge.databinding.EditChallengeFragmentLayoutBinding
import com.sign.dayschallenge.ui.MainActivity
import com.sign.dayschallenge.utils.Constants
import com.sign.dayschallenge.utils.ImageSelectorDialogFragment
import com.sign.dayschallenge.viewmodel.ChallengeViewModel
import kotlinx.android.synthetic.main.create_challenge_fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class EditChallengeFragment : Fragment(R.layout.edit_challenge_fragment_layout) {


    private val args : EditChallengeFragmentArgs by navArgs()
    private var _binding : EditChallengeFragmentLayoutBinding? = null
    private val binding get() = _binding!!
    private var resourceImage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewWithArgs()

        binding.btnSave.setOnClickListener {
            val challenge = args.argChallenge
            challenge.title = binding.tvTitle.editText?.text.toString().trim()
            challenge.description = binding.tvDescription.editText?.text.toString().trim()
            if (resourceImage!=0) challenge.imgResource = resourceImage
            (activity as MainActivity).viewModelApp.updateChallenge(challenge)
            findNavController().popBackStack()
        }

        binding.selectorImg.setOnClickListener {
            val dialog = ImageSelectorDialogFragment()
            dialog.setTargetFragment(this,MY_REQUEST_CODE )
            dialog.show(fragmentManager as FragmentManager,"my_dialog")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditChallengeFragmentLayoutBinding.inflate(inflater,container, false)
        return binding.root
    }

    private fun initViewWithArgs(){
        binding.ivChallenge.setImageResource(args.argChallenge.imgResource)
        binding.tvTitle.editText?.setText(args.argChallenge.title)
        binding.tvDescription.editText?.setText(if (args.argChallenge.description!=null) args.argChallenge.description else "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==MY_REQUEST_CODE){
            if (resultCode == MY_REQUEST_CODE){
                data?.let {
                    resourceImage = it.getIntExtra(Constants.IMAGE_RESOURCE,0)
                    iv_challenge.setImageResource(resourceImage) }
            }
        }
    }

}