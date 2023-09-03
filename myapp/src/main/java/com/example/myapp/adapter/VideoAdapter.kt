package com.example.myapp.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.MyApplication.TAG
import com.example.myapp.R
import com.example.myapp.api.OkApi
import com.example.myapp.api.CallBack
import com.example.myapp.entity.VideoEntity
import com.example.myapp.linstener.OnItemClickListener
import com.squareup.picasso.Picasso

class VideoAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context

    private lateinit var mInfo: List<VideoEntity>

    lateinit var mOnItemClickListener: OnItemClickListener

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

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
        val vh = holder as ViewHolder
        val videoEntity = mInfo[position]
        vh.tvTitle.text = videoEntity.vtitle
        vh.tvAuthor.text = videoEntity.author
        vh.tvDz.text = videoEntity.likeNum.toString()
        vh.tvComment.text = videoEntity.commentNum.toString()
        vh.tvCollect.text = videoEntity.collectNum.toString()

        Picasso.with(mContext).load(videoEntity.headurl).into(vh.img_header)
        Picasso.with(mContext).load(videoEntity.coverurl).into(vh.img_cover)
        vh.mPosition = position
        vh.entity = mInfo[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var tvAuthor: TextView = itemView.findViewById(R.id.author)
        var tvDz: TextView = itemView.findViewById(R.id.dz)
        var tvComment: TextView = itemView.findViewById(R.id.comment)
        var tvCollect: TextView = itemView.findViewById(R.id.collect)
        var img_header: ImageView = itemView.findViewById(R.id.img_header)
        var img_cover: ImageView = itemView.findViewById(R.id.img_cover)
        var img_comment: ImageView = itemView.findViewById(R.id.img_comment)
        var img_collect: ImageView = itemView.findViewById(R.id.img_collect)
        var dz: ImageView = itemView.findViewById(R.id.img_like)

        var flagCollect = false
        var flagLike = false
        var mPosition: Int = 0

        lateinit var entity: VideoEntity

        init {
            itemView.setOnClickListener {
                mOnItemClickListener.onItemClick(entity)
            }
            img_collect.setOnClickListener {
                var collectNum = tvCollect.text.toString().toInt()
                if (flagCollect) {
                    // 已收藏， 点击后收藏数减1， 文字颜色白色， 图片为收藏样式， post请求保存
                    if (collectNum > 0) {
                        --collectNum
                        tvCollect.text = collectNum.toString()
                        tvCollect.setTextColor(Color.parseColor("#161616"))
                        img_collect.setImageResource(R.mipmap.collect)
                        // 保存到数据库
                        updateCount(mInfo[mPosition].vid, 1, !flagCollect)
                    }
                } else {
                    ++collectNum
                    tvCollect.text = collectNum.toString()
                    tvCollect.setTextColor(Color.parseColor("#B22222"))
                    img_collect.setImageResource(R.mipmap.collect_select)
                    updateCount(mInfo[mPosition].vid, 1, !flagCollect)
                }
                flagCollect = !flagCollect
            }
            dz.setOnClickListener {
                var dzNum = tvDz.text.toString().toInt()
                if (flagLike) {
                    if (dzNum > 0) {
                        --dzNum
                        tvDz.text = dzNum.toString()
                        tvDz.setTextColor(Color.parseColor("#161616"))
                        dz.setImageResource(R.mipmap.dianzan)
                        updateCount(mInfo[mPosition].vid, 2, !flagLike)
                    }
                } else {
                    ++dzNum
                    tvDz.text = dzNum.toString()
                    tvDz.setTextColor(Color.parseColor("#B22222"))
                    dz.setImageResource(R.mipmap.dianzan_select)
                    updateCount(mInfo[mPosition].vid, 2, !flagLike)
                }
                flagLike = !flagLike
            }
            // 通过tag 将ViewHolder和itemView绑定
            itemView.tag = this
        }

        private fun updateCount(vid: Int, i1: Int, b: Boolean) {
            val map = mutableMapOf<String, Any>()
            map.put("type", i1)
            map.put("vid", vid)
            map.put("flag", b)
            OkApi.config("app/videolist/updateCount", map).postRequest(mContext, object : CallBack {
                override fun onSuccess(res: String) {
                    Log.d(TAG, "updateCount已执行" + res)
                }

                override fun onFailure(t: Throwable) {
                    //TODO("Not yet implemented")
                    Log.d(TAG, "updateCount已执行" + t)
                }
            })
        }
    }
}
