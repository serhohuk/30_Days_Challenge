package com.sign.dayschallenge.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sign.dayschallenge.R
import com.sign.dayschallenge.data.Challenge
import kotlinx.android.synthetic.main.challenge_item_layout.view.*

class ChallengeAdapter : RecyclerView.Adapter<ChallengeAdapter.MyViewHolder>()  {

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.challenge_item_layout,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val challenge = differ.currentList[position]
        holder.itemView.apply {
            val passedDays = challenge.daysPassed.toString()+"/30"
            tv_progress.text = passedDays
            imView.setImageResource(challenge.imgResource)
            tv_title.text = challenge.title

            setOnClickListener {
                onItemClickListener?.let { it(challenge)  }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Challenge>(){
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem==newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    private var onItemClickListener : ((Challenge)->Unit)? = null

    fun setOnItemClickListener(listener : (Challenge)->Unit){
        onItemClickListener = listener
    }
}

class ChallengeItemDecorator(private val space : Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = space
        outRect.left = space
        if ((parent.getChildAdapterPosition(view))%10==4 || (parent.getChildAdapterPosition(view))%10==9){
            outRect.right = space
        }
    }
}