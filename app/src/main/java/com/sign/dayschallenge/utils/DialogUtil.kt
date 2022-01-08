package com.sign.dayschallenge.utils

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.sign.dayschallenge.R
import java.lang.Exception

object DialogUtil {

    fun changeDayStateDialog(context : Context, actionResponse: ActionResponse<Boolean>) : Dialog{
        val dialogView : View = LayoutInflater.from(context).inflate(R.layout.daystatedialog_layout,null)
        val tvSkip = dialogView.findViewById<TextView>(R.id.tv_skip)
        val tvComplete = dialogView.findViewById<TextView>(R.id.tv_complete)

        val dialog = Dialog(context)
        dialog.setContentView(dialogView)
        dialog.setCancelable(true)
        if (dialog.window != null) dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        try {
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        tvSkip.setOnClickListener {
            actionResponse.onResponse(true)
            dialog.dismiss()
        }
        tvComplete.setOnClickListener {
            actionResponse.onResponse(true)
            dialog.dismiss()
        }

        return dialog
    }

}