package com.example.myapp.activities

import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import cc.shinichi.library.ImagePreview
import cc.shinichi.library.view.listener.OnBigImageClickListener
import com.example.myapp.MyApplication.TAG
import com.example.myapp.R
import com.example.myapp.databinding.ActivityNews2Binding
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso


class NewsActivity2 : BaseActivity<ActivityNews2Binding>(), View.OnClickListener {
    override fun initBinding(): ActivityNews2Binding {
        return ActivityNews2Binding.inflate(layoutInflater)
    }

    lateinit var entity: NewsEntity
    private val imgList = mutableListOf<String>()

    override fun initData() {
        val bundle = intent.extras
        if (bundle != null) {
            entity = bundle.getSerializable("newsEntity") as NewsEntity
            initContent(entity)
        }
        for (thumbListBean in entity.newsThumbList) {
            imgList.add(thumbListBean.thumbUrl)
        }
        vb.imgCover1.setOnClickListener(this)
        vb.imgCover2.setOnClickListener(this)
        vb.imgCover3.setOnClickListener(this)
    }

    private fun initContent(entity: NewsEntity) {
        vb.tvTitle.text = entity.newsTitle

        Picasso.with(this).load(entity.headerUrl).transform(CircleTransformation())
            .into(vb.imgHeader)
        Picasso.with(this).load(entity.newsThumbList[0].thumbUrl).into(vb.imgCover1)
        Picasso.with(this).load(entity.newsThumbList[1].thumbUrl).into(vb.imgCover2)
        Picasso.with(this).load(entity.newsThumbList[2].thumbUrl).into(vb.imgCover3)

        vb.tvAuthor.text = entity.authorName
        vb.tvCreateTime.text = entity.releaseDate
        vb.desc.text = entity.newsTitle
        vb.tvComment.text = "评论 ${entity.commentCount}"
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.img_cover1 -> startImagePreview(0)
            R.id.img_cover2 -> startImagePreview(1)
            R.id.img_cover3 -> startImagePreview(2)
        }
    }

    private fun startImagePreview(index: Int) {
        ImagePreview
            .instance
            .setContext(this)
            .setIndex(index)
            .setImageList(imgList)
            // 是否启用下拉关闭。默认不启用
            .setEnableDragClose(true)
            // 是否启用上拉关闭。默认不启用
            .setEnableUpDragClose(true)
            // 是否显示下载按钮，在页面右下角。默认显示
            .setShowDownButton(true)
            // 点击图片回调
            .setBigImageClickListener(object :OnBigImageClickListener{
                override fun onClick(activity: Activity?, view: View?, position: Int) {
                    Log.d(TAG, "onClick: 当前第$position 个，view是$view");
                }
            })
            .start()
    }
}