package com.sign.dayschallenge.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.sign.dayschallenge.R
import kotlinx.android.synthetic.main.challenge_fragment_layout.*

class ChallengeFragment : Fragment(R.layout.challenge_fragment_layout) {

    private val args : ChallengeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_challenge_icon.setImageDrawable(context?.getDrawable(args.argChallenge.imgResource))

    }
}