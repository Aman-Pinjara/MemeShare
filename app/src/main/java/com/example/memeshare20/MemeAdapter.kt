package com.example.memeshare20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MemeAdapter(private val listener: MemeClicked) : RecyclerView.Adapter<MemeView>() {
    private val items :ArrayList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemeView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meme_item, parent, false)
        val memeView = MemeView(view)
        view.setOnClickListener{
            listener.itemClicked(items[memeView.adapterPosition])
        }
        return memeView
    }

    override fun onBindViewHolder(holder: MemeView, position: Int) {
        val currentPos = items[position]
        Glide.with(holder.itemView.context).load(currentPos).into(holder.memeImage)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(updated:ArrayList<String>){
        items.clear()
        items.addAll(updated)
        notifyDataSetChanged()
    }
}

class MemeView(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val memeImage:ImageView = itemView.findViewById(R.id.memeImage)
}

interface MemeClicked{
    fun itemClicked(item: String)
}