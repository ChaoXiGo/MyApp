package com.example.myapp.activities

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.MyCollectAdapter
import com.example.myapp.api.OkApi
import com.example.myapp.api.CallBack
import com.example.myapp.databinding.ActivityMyCollectBinding
import com.example.myapp.entity.VideoEntity
import com.example.myapp.entity.VideoListEntity
import com.google.gson.Gson

class MyCollectActivity : BaseActivity<ActivityMyCollectBinding>() {
    override fun initBinding(): ActivityMyCollectBinding {
        return ActivityMyCollectBinding.inflate(layoutInflater)
    }
    @SuppressLint("HandlerLeak")
    val handler: Handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0){
                adapter.setInfo(collectList)
                // 2`设置当前recycleView的adapter
                vb.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    lateinit var adapter:MyCollectAdapter

    override fun initData() {
        // 1`初始化recycleView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager

        // 2`创建adapter
        adapter = MyCollectAdapter(this)

        getCollectInfo()
    }

    var collectList = ArrayList<VideoEntity>()
    private fun getCollectInfo() {
        OkApi.config("app/news/list", mutableMapOf()).getRequest(this, object : CallBack {
            override fun onSuccess(res: String) {
                val response = Gson().fromJson(res, VideoListEntity::class.java)
                if (response.code == 1 && response != null) {
                    val list = response.data
                    collectList.addAll(list)
                    handler.sendEmptyMessage(0)
                }
            }

            override fun onFailure(t: Throwable) {

            }
        })
    }
}