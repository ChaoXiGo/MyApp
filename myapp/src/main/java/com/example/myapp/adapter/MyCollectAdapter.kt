package com.example.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.entity.NewsEntity
import com.squareup.picasso.Picasso

class MyCollectAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData = mutableListOf<NewsEntity>()
    fun setInfo(data: MutableList<NewsEntity>){
        mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_collect_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolder
        val entity = mData[position]
        vh.tvTitle.text = entity.newsTitle
        vh.tvAuthor.text = entity.authorName

        Picasso.with(mContext).load(entity.headerUrl).into(vh.img_header)
        // Picasso.with(mContext).load(entity.).into(vh.img_cover)
    }

    class ViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var tvAuthor: TextView = itemView.findViewById(R.id.author)
        var img_header: ImageView = itemView.findViewById(R.id.img_header)
        var img_cover: ImageView = itemView.findViewById(R.id.img_cover)

    }
}