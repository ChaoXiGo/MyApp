package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.MyApplication.TAG
import com.example.myapp.adapter.MyCollectAdapter
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.ActivityMyCollectBinding
import com.example.myapp.entity.NewsResponse
import com.example.myapp.entity.VideoResponse.DataBean.VideoEntity
import com.example.myapp.linstener.OnItemClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class MyCollectActivity : BaseActivity<ActivityMyCollectBinding>() {
    override fun initBinding(): ActivityMyCollectBinding {
        return ActivityMyCollectBinding.inflate(layoutInflater)
    }

    @SuppressLint("HandlerLeak")
    val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
                adapter.setInfo(collectList)
                // 2`设置当前recycleView的adapter
                vb.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    lateinit var adapter: MyCollectAdapter
    private var pageNum = 1
    override fun initData() {
        // 1`初始化recycleView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager

        // 2`创建adapter
        adapter = MyCollectAdapter(this)

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Serializable) {
                val bundle = Bundle().apply {
                    putSerializable("videoEntity", obj)
                }
                val intent = Intent(this@MyCollectActivity, WebActivity::class.java).apply {
                    putExtras(bundle)
                }

                /* val bundle = Bundle()
                bundle.putSerializable("videoEntity", obj)
                val intent = Intent(this@MyCollectActivity, WebActivity::class.java)
                intent.putExtras(bundle) */
                startActivity(intent)
            }
        })

        vb.refreshLayout.setOnRefreshListener {
            pageNum = 1
            getCollectInfo(true)
        }
        vb.refreshLayout.setOnLoadMoreListener {
            pageNum++
            getCollectInfo(false)
        }
        getCollectInfo(true)
    }

    private var collectList = mutableListOf<VideoEntity>()

    @SuppressLint("CheckResult")
    private fun getCollectInfo(isRefresh: Boolean) {
        RetrofitApi.config(this)
            .getCollectList(pageNum, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                if (it.code == 1 && it.data.size > 0) {
                    val list = it.data.videoEntity
                    if (list != null && list.size > 0) {
                        if (isRefresh) {
                            collectList = list
                        } else {
                            collectList.addAll(list)
                        }
                        handler.sendEmptyMessage(0)
                    }
                } else {
                    if (isRefresh) {
                        showToast("暂时无数据")
                    } else {
                        showToast("没有更多数据")
                    }
                }
            }, {
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                showToast("收藏获取失败")
            })


        /* OkApi.config("app/news/list", mutableMapOf()).getRequest(this, object : CallBack {
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
             }) */
    }
}