package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.api.RetrofitApi
import com.example.myapp.entity.VideoResponse.DataBean.VideoEntity
import com.example.myapp.linstener.OnItemChildClickListener
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import xyz.doikki.videocontroller.component.PrepareView

class MyCollectAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mContext = context
    private var mData = mutableListOf<VideoEntity>()
    fun setInfo(data: MutableList<VideoEntity>) {
        mData = data
    }

    private lateinit var mOnItemChildClickListener: OnItemChildClickListener

    fun setOnItemClickListener(onItemChildClickListener:OnItemChildClickListener){
        mOnItemChildClickListener = onItemChildClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.item_video_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = mData[position]
        val vh = holder as MyCollectAdapter.ViewHolder
        vh.tvTitle.text = entity.vtitle
        vh.tvAuthor.text = entity.author
        vh.tvDz.text = entity.likeNum.toString()
        vh.tvComment.text = entity.commentNum.toString()
        vh.tvCollect.text = entity.collectNum.toString()

        Picasso.with(mContext).load(entity.headurl).transform(CircleTransformation()).into(vh.img_header)
        Picasso.with(mContext).load(entity.coverurl).into(vh.thumb)
        // val playurl = entity.playurl
        vh.mPosition = position
        // 初始化收藏点赞状态
        vh.collectState = entity.collectState == 1
        vh.likeState = entity.likeState == 1
        if (vh.collectState) {
            // updateUIState(collectState,vh.tvCollect,vh.img_collect,R.mipmap.collect_select,R.mipmap.collect)
            vh.tvCollect.setTextColor(Color.parseColor("#B22222"))
            vh.img_collect.setImageResource(R.mipmap.collect_select)
        }
        if (vh.likeState) {
            vh.tvDz.setTextColor(Color.parseColor("#B22222"))
            vh.dz.setImageResource(R.mipmap.dianzan_select)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var tvAuthor: TextView = itemView.findViewById(R.id.author)
        var tvDz: TextView = itemView.findViewById(R.id.dz)
        var tvComment: TextView = itemView.findViewById(R.id.comment)
        var tvCollect: TextView = itemView.findViewById(R.id.collect)
        var img_header: ImageView = itemView.findViewById(R.id.img_header)
        var ll_comment: LinearLayout = itemView.findViewById(R.id.ll_comment)
        var ll_collect: LinearLayout = itemView.findViewById(R.id.ll_collect)
        var ll_like: LinearLayout = itemView.findViewById(R.id.ll_like)
        var img_comment: ImageView = itemView.findViewById(R.id.img_comment)
        var img_collect: ImageView = itemView.findViewById(R.id.img_collect)
        var dz: ImageView = itemView.findViewById(R.id.img_like)

        var playerController: FrameLayout = itemView.findViewById(R.id.player_container)
        var prepareView: PrepareView = itemView.findViewById(R.id.prepare_view)
        var thumb: ImageView = prepareView.findViewById(R.id.thumb)

        var collectState: Boolean = false
        var likeState: Boolean = false

        var mPosition : Int = -1

        init {
            view.setOnClickListener{
                mOnItemChildClickListener.OnItemChildClick(mPosition)
            }
            ll_comment.setOnClickListener(this)
            ll_collect.setOnClickListener(this)
            ll_like.setOnClickListener(this)
            // 通过tag 将ViewHolder和itemView绑定
            view.tag = this
        }

        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.ll_comment ->{
                    Toast.makeText(mContext, "评论暂未实现", Toast.LENGTH_SHORT).show()
                }
                R.id.ll_collect -> {
                    var collectNum = tvCollect.text.toString().toInt()
                    if (collectState) {
                        // 已收藏， 点击后收藏数减1， 文字颜色白色， 图片为收藏样式
                        if (collectNum > 0) {
                            --collectNum
                            tvCollect.text = collectNum.toString()
                            tvCollect.setTextColor(Color.parseColor("#161616"))
                            img_collect.setImageResource(R.mipmap.collect)
                            // 保存到数据库
                            updateCount(mData[mPosition].vid, 1, !collectState)
                        }
                    } else {
                        ++collectNum
                        tvCollect.text = collectNum.toString()
                        tvCollect.setTextColor(Color.parseColor("#B22222"))
                        img_collect.setImageResource(R.mipmap.collect_select)
                        updateCount(mData[mPosition].vid, 1, !collectState)
                    }
                    collectState = !collectState
                }

                R.id.ll_like -> {
                    var dzNum = tvDz.text.toString().toInt()
                    if (likeState) {
                        if (dzNum > 0) {
                            --dzNum
                            tvDz.text = dzNum.toString()
                            tvDz.setTextColor(Color.parseColor("#161616"))
                            dz.setImageResource(R.mipmap.dianzan)
                            updateCount(mData[mPosition].vid, 2, !likeState)
                        }
                    } else {
                        ++dzNum
                        tvDz.text = dzNum.toString()
                        tvDz.setTextColor(Color.parseColor("#B22222"))
                        dz.setImageResource(R.mipmap.dianzan_select)
                        updateCount(mData[mPosition].vid, 2, !likeState)
                    }
                    likeState = !likeState
                }
            }
        }

        @SuppressLint("CheckResult")
        private fun updateCount(vid: Int, type: Int, flag: Boolean) {
            RetrofitApi.config(mContext)
                .updateCount(vid, type, flag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show()
                }, {
                    Toast.makeText(mContext, "网络连接超时", Toast.LENGTH_SHORT).show()
                })
        }
    }
}