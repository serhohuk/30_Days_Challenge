package com.sign.dayschallenge.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.sign.dayschallenge.R

class GridAdapter(private val context : Context,
    private val images : List<Int>) : BaseAdapter() {

    override fun getCount(): Int {
        return images.size
    }

    override fun getItem(position: Int): Any {
        return images.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var item = View.inflate(context, R.layout.grid_image_item,null)
        val image : ImageView = item.findViewById(R.id.iv_grid_item)
        image.setImageResource(images.get(position))

        return item
    }

}