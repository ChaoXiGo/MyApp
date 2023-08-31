package com.example.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.entity.VideoEntity
import com.squareup.picasso.Picasso

class VideoAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private lateinit var mInfo: List<VideoEntity>

    fun setInfo(info: List<VideoEntity>) {
        mInfo = info
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mInfo.isNotEmpty()) mInfo.size else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val videoEntity = mInfo[position]
        viewHolder.tvTitle.text = videoEntity.vtitle
        viewHolder.tvAuthor.text = videoEntity.author
        viewHolder.tvDz.text = videoEntity.likeNum.toString()
        viewHolder.tvComment.text = videoEntity.commentNum.toString()
        viewHolder.tvCollect.text = videoEntity.collectNum.toString()

        Picasso.with(mContext).load(videoEntity.headurl).into(viewHolder.img_header)
        Picasso.with(mContext).load(videoEntity.coverurl).into(viewHolder.img_cover)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvAuthor: TextView
        var tvDz: TextView
        var tvComment: TextView
        var tvCollect: TextView
        var img_header: ImageView
        var img_cover: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.title)
            tvAuthor = itemView.findViewById(R.id.author)
            tvDz = itemView.findViewById(R.id.dz)
            tvComment = itemView.findViewById(R.id.comment)
            tvCollect = itemView.findViewById(R.id.collect)
            img_header = itemView.findViewById(R.id.img_header)
            img_cover = itemView.findViewById(R.id.img_cover)
        }
    }

}
