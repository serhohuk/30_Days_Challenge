package com.sign.dayschallenge.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.fragment.app.DialogFragment
import com.sign.dayschallenge.R
import com.sign.dayschallenge.adapters.GridAdapter
import com.sign.dayschallenge.utils.Constants.Companion.IMAGE_RESOURCE
import kotlinx.android.synthetic.main.image_selector_dialog_fragment.view.*

class ImageSelectorDialogFragment : DialogFragment(), AdapterView.OnItemClickListener {

    private val resultCode = 27
    private var images : MutableList<Int>? = null
    private var imageItemResource : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView = inflater.inflate(R.layout.image_selector_dialog_fragment,container,false)

         images = mutableListOf(R.drawable.book, R.drawable.hard_work, R.drawable.chain,
            R.drawable.money, R.drawable.mission, R.drawable.motivation, R.drawable.reward,
            R.drawable.weight)

        val adapter = GridAdapter(requireContext(),images!!)
        val gridView = rootView.findViewById<GridView>(R.id.grid_view)
        gridView.adapter = adapter
        gridView.onItemClickListener = this

        rootView.submit_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra(IMAGE_RESOURCE, imageItemResource)
            getTargetFragment()?.onActivityResult(getTargetRequestCode(), resultCode , intent);
            dismiss()
        }

        rootView.cancel_button.setOnClickListener {
            dismiss()
        }

        return rootView
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        imageItemResource = images?.get(position)!!
    }

}