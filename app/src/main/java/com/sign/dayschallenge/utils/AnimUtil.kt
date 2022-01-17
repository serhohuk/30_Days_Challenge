package com.sign.dayschallenge.utils

import android.view.View
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.main_fragment_layout.*

class AnimUtil {

    companion object{
        fun rotateView(deegres : Float, view : View){
            val rotateAnim = RotateAnimation(
                0.0f, deegres,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f
            )
            rotateAnim.duration = 100
            rotateAnim.fillAfter = true
            view.startAnimation(rotateAnim)
        }
    }
}