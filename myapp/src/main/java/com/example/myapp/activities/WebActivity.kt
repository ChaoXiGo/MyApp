package com.example.myapp.activities

import android.annotation.SuppressLint
import com.example.myapp.databinding.ActivityWebBinding
import com.example.myapp.jsbridge.BridgeHandler
import com.example.myapp.jsbridge.CallBackFunction

class WebActivity : BaseActivity<ActivityWebBinding>() {

    override fun initBinding(): ActivityWebBinding {
        return ActivityWebBinding.inflate(layoutInflater)
    }

    private lateinit var url:String

    @SuppressLint("SetJavaScriptEnabled")
    override fun initData() {
        // fragment点击事件通过bundle获得url信息，
        url = intent.extras?.getString("url").toString()
       /*  val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("url")!!
        } */
        registerJavaHandler()
        initWebView()
    }

    private fun initWebView(){
        // 打开js链接
        val settings = vb.BridgeWebView.settings
        settings.javaScriptEnabled = true

        // 加载url
        vb.BridgeWebView.loadUrl(url)
    }

    /**
     * java与js联调方法
     * js方法被调用时执行此处代码
     */
    private fun registerJavaHandler(){
        vb.BridgeWebView.registerHandler("goback",object :BridgeHandler{
            override fun handler(data: String?, function: CallBackFunction?) {
                finish()
            }
        })
    }
}