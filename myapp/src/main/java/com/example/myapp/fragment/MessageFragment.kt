package com.example.myapp.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.MyApplication.TAG
import com.example.myapp.activities.WebActivity
import com.example.myapp.adapter.MessageAdapter
import com.example.myapp.api.OkApi
import com.example.myapp.api.CallBack
import com.example.myapp.databinding.FragmentMessageBinding
import com.example.myapp.entity.NewsEntity
import com.example.myapp.entity.NewsResponse
import com.example.myapp.linstener.OnItemClickListener
import com.google.gson.Gson
import java.io.Serializable

class MessageFragment : BaseFragment<FragmentMessageBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {

            }
    }
    @SuppressLint("HandlerLeak")
    val handler: Handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0){
                adapter.setInfo(data)
                // 2`设置当前recycleView的adapter
                vb.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMessageBinding {
        return FragmentMessageBinding.inflate(inflater,container,false)
    }

    lateinit var adapter:MessageAdapter
    var pageNum = 1
    override fun initData() {
        // 1`初始化recycleView
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager

        adapter = MessageAdapter(requireContext())

        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Serializable) {
                // 点击当前view的时候调用view的点击监听事件，事件中执行自定义的接口方法返回当前view中信息, 打开一个Activity
                val newsEntity = obj as NewsEntity
                Log.d(TAG, "onItemClick: ${newsEntity.authorName}")

                val url = "http://192.168.31.32:8089/newsDetail?title=${newsEntity.authorName}"
                val bundle = Bundle()
                bundle.putString("url",url)
                val intent = Intent(activity, WebActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)

                // startActivity(Intent(activity, WebActivity::class.java),Bundle(bundle))
            }

        })
        vb.refreshLayout.setOnRefreshListener{
            pageNum = 1
            getMessageList(true)
        }
        vb.refreshLayout.setOnLoadMoreListener{
            pageNum ++
            getMessageList(false)
        }

        // 3`接口获取信息
        getMessageList(true)
    }

    var data = mutableListOf<NewsEntity>()
    private fun getMessageList(isRefresh:Boolean) {
        val map = mutableMapOf<String, Any>()
        OkApi.config("app/news/list",map).getRequest(context,object :CallBack{
            override fun onSuccess(res: String) {
                if(isRefresh){
                    vb.refreshLayout.finishRefresh(true)
                }else{
                    vb.refreshLayout.finishLoadMore(true)
                }
                val response = Gson().fromJson(res, NewsResponse::class.java)
                if (response.code == 1 && response != null){
                    val list = response.data
                    if (isRefresh){
                        data = list
                    }else {
                        data.addAll(list)
                    }
                    handler.sendEmptyMessage(0)
                }else{
                    if (isRefresh) {
                        showToastSync("暂时无数据")
                    } else {
                        showToastSync("没有更多数据")
                    }
                }
            }

            override fun onFailure(t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}