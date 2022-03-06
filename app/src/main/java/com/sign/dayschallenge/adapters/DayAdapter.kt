package com.sign.dayschallenge.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sign.dayschallenge.R
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.data.DayState

class DayAdapter(val context : Context) : RecyclerView.Adapter<DayAdapter.DayItemViewHolder>() {

    var itemCLickListener : ((Int, Int)->Unit)? = null

    private val differUtilCallback = object : DiffUtil.ItemCallback<Pair<Long,DayState>>(){
        override fun areItemsTheSame(oldItem: Pair<Long,DayState>, newItem: Pair<Long,DayState>): Boolean {
            return oldItem.first == newItem.first
        }

        override fun areContentsTheSame(oldItem: Pair<Long,DayState>, newItem: Pair<Long,DayState>): Boolean {
            return oldItem == newItem
        }
    }

    val differAsync = AsyncListDiffer(this,differUtilCallback)


    inner class DayItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val itemCardView : CardView
        private val textNumber : TextView

        init {
            itemCardView = itemView.findViewById(R.id.card_view_day)
            textNumber = itemView.findViewById(R.id.tv_number)
//            itemView.layoutParams = ViewGroup.LayoutParams()
        }

        fun bind(text : String, dayStateValue : Int,position: Int){
            textNumber.text = text
            when(dayStateValue){
                DayState.EMPTY.ordinal->{
                    textNumber.setTextColor(context.resources.getColor(R.color.design_default_color_primary_dark))
                    itemCardView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.color_state_empty))
                }
                DayState.COMPLETE_DAY.ordinal->{
                    itemCardView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.color_state_complete))
                }
                DayState.SKIP_DAY.ordinal ->{
                    itemCardView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.color_state_skip))
                }
                DayState.TODAY.ordinal->{
                    itemCardView.backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.color_state_today))
                }
            }

            itemCardView.setOnLongClickListener {
                itemCLickListener?.invoke(dayStateValue,position)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItemViewHolder {
        return DayItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day_adapter, parent,false))
    }

    override fun onBindViewHolder(holder: DayItemViewHolder, position: Int) {
        holder.bind((position+1).toString(),differAsync.currentList[position].second.ordinal, position)
    }

    override fun getItemCount(): Int {
        return differAsync.currentList.size
    }
}