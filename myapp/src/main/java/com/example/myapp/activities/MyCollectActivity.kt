package com.example.myapp.activities

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.MyCollectAdapter
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.ActivityMyCollectBinding
import com.example.myapp.entity.NewsEntity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

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

    var collectList = mutableListOf<NewsEntity>()

    @SuppressLint("CheckResult")
    private fun getCollectInfo(isRefresh: Boolean) {
        RetrofitApi.config(this)
            .getCollectList(pageNum, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isRefresh){
                    vb.refreshLayout.finishRefresh(true)
                }else{
                    vb.refreshLayout.finishLoadMore(true)
                }
                if (it.code == 1 && it.data.size > 0) {
                    val list = it.data
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
        /*  OkApi.config("app/news/list", mutableMapOf()).getRequest(this, object : CallBack {
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