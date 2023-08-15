package com.example.myapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.entity.VideoUser
import kotlin.Int

class VideoAdapter(_context: Context, _info: List<VideoUser>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = _context
    private val mInfo = _info
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mInfo.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val videoUser = mInfo[position]
        val viewHolder = holder as ViewHolder
        viewHolder.id.text = videoUser.id.toString()
        viewHolder.username.text = videoUser.username
        viewHolder.password.text = videoUser.password
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val id: TextView
         val username: TextView
         val password: TextView

        init {
            id = itemView.findViewById(R.id.id)
            username = itemView.findViewById(R.id.username)
            password = itemView.findViewById(R.id.password)
        }
    }

}
