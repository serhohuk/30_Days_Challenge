package com.sign.dayschallenge.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sign.dayschallenge.R
import com.sign.dayschallenge.application.MyApplication
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.data.DayState
import com.sign.dayschallenge.utils.Constants.Companion.IMAGE_RESOURCE
import com.sign.dayschallenge.utils.ImageSelectorDialogFragment
import com.sign.dayschallenge.utils.Resource
import com.sign.dayschallenge.viewmodel.ChallengeViewModel
import kotlinx.android.synthetic.main.create_challenge_fragment_layout.*
import javax.inject.Inject
import javax.inject.Named

class CreateChallengeFragment : Fragment(R.layout.create_challenge_fragment_layout) {

    @Inject
    @Named("challenge_view_model")
    lateinit var challengeViewModelFactory : ViewModelProvider.Factory
    val viewModel : ChallengeViewModel by viewModels { challengeViewModelFactory  }
    val MY_REQUEST_CODE = 27
    private var resourceImage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appComponent = (requireActivity().application as MyApplication).appComponent
        appComponent.inject(this)

        btn_save.setOnClickListener {
            if (tv_title.editText?.text.toString().isEmpty()){
                Snackbar.make(view,getString(R.string.fill_title),Snackbar.LENGTH_SHORT).setAction(getString(R.string.hide),null).show()
            }
            else if (resourceImage==0){
                Snackbar.make(view,getString(R.string.choose_image),Snackbar.LENGTH_SHORT).setAction(getString(R.string.hide),null).show()
            }
            else{
                viewModel.addChallenge(getChallenge())
                findNavController().popBackStack()
            }

        }

        selector_img.setOnClickListener {
            val dialog = ImageSelectorDialogFragment()
            dialog.setTargetFragment(this,MY_REQUEST_CODE )
            dialog.show(fragmentManager as FragmentManager,"my_dialog")
        }
    }

    private fun getChallenge() : Challenge{
        val title = tv_title.editText?.text.toString()
        val description = tv_description.editText?.text.toString()
        return Challenge(0,title,resourceImage,description,0, listOf(DayState.EMPTY))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode==MY_REQUEST_CODE){
            if (resultCode == MY_REQUEST_CODE){
                data?.let {
                    resourceImage = it.getIntExtra(IMAGE_RESOURCE,0)
                    selector_img.setImageResource(resourceImage) }
            }
        }
    }



}