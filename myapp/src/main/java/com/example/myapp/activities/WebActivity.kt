package com.example.myapp.activities

import android.annotation.SuppressLint
import android.util.Log
import com.example.myapp.MyApplication.TAG
import com.example.myapp.databinding.ActivityWebBinding
import com.example.myapp.entity.VideoResponse
import com.example.myapp.view.CircleTransformation
import com.squareup.picasso.Picasso
import kotlin.math.round

class WebActivity : BaseActivity<ActivityWebBinding>() {

    override fun initBinding(): ActivityWebBinding {
        return ActivityWebBinding.inflate(layoutInflater)
    }

    // private lateinit var url:String
    lateinit var entity: VideoResponse.DataBean.VideoEntity

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        // fragment点击事件通过bundle获得url信息，
        // url = intent.extras?.getString("url").toString()
        val bundle = intent.extras
        if (bundle != null) {
            // url = bundle.getString("url")!!
            // entity = bundle.getSerializable("newsEntity") as VideoEntity
            val entity = bundle.getSerializable("videoEntity") as VideoResponse.DataBean.VideoEntity
            initContent(entity)
        }

        vb.imgCover.setOnClickListener{
            showToast("放大图片")
        }
        // registerJavaHandler()
        // initWebView()
    }

    private fun initContent(entity: VideoResponse.DataBean.VideoEntity) {

        vb.tvTitle.text = entity.author

        Picasso.with(this).load(entity.headurl).transform(CircleTransformation()).into(vb.imgHeader)
        Picasso.with(this).load(entity.coverurl).into(vb.imgCover)

        vb.tvAuthor.text = entity.author
        vb.tvCreateTime.text = entity.createTime
        vb.desc.text = entity.vtitle

        vb.tvComment.text = "评论 ${entity.commentNum}"
        vb.tvLike.text = "${entity.likeNum} 赞"
    }

    private fun initWebView() {
        // 打开js链接
        // val settings = vb.BridgeWebView.settings
        // settings.javaScriptEnabled = true

        // 加载url
        // vb.BridgeWebView.loadUrl(url)
    }


    /**
     * java与js联调方法
     * js方法被调用时执行此处代码
     */
    private fun registerJavaHandler() {
        /*  vb.BridgeWebView.registerHandler("goback",object :BridgeHandler{
             override fun handler(data: String?, function: CallBackFunction?) {
                 finish()
             }
         }) */
    }
}