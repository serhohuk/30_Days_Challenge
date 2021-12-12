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

    private val differUtilCallback = object : DiffUtil.ItemCallback<DayState>(){
        override fun areItemsTheSame(oldItem: DayState, newItem: DayState): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DayState, newItem: DayState): Boolean {
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

        fun bind(text : String, dayStateValue : Int){
            textNumber.text = text
            when(dayStateValue){
                DayState.EMPTY.ordinal->{
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItemViewHolder {
        return DayItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_day_adapter, parent,false))
    }

    override fun onBindViewHolder(holder: DayItemViewHolder, position: Int) {
        holder.bind((position+1).toString(),differAsync.currentList[position].ordinal)
    }

    override fun getItemCount(): Int {
        return differAsync.currentList.size
    }
}