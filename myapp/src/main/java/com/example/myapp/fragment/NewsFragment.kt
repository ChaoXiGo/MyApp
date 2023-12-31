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
import com.example.myapp.activities.LoginActivity
import com.example.myapp.activities.NewsActivity
import com.example.myapp.activities.NewsActivity2
import com.example.myapp.adapter.NewsAdapter
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.FragmentNewsBinding
import com.example.myapp.entity.NewsResponse.NewsEntity
import com.example.myapp.linstener.OnItemClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {}
    }

    @SuppressLint("HandlerLeak")
    val handler: Handler = object : Handler() {
        @SuppressLint("NotifyDataSetChanged")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 0) {
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
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    lateinit var adapter: NewsAdapter
    private var pageNum = 1
    override fun initData() {
        // 1`初始化recycleView
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager
        // 初始化adapter
        adapter = NewsAdapter(requireContext())
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Serializable) {
                val newsEntity = obj as NewsEntity
                if (newsEntity.type != 2) {
                    val bundle = Bundle()
                    bundle.putSerializable("newsEntity", obj)
                    val intent = Intent(activity, NewsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    val bundle = Bundle()
                    bundle.putSerializable("newsEntity", obj)
                    val intent = Intent(activity, NewsActivity2::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
        })
        vb.refreshLayout.setOnRefreshListener {
            pageNum = 1
            getMessageList(true)
        }
        vb.refreshLayout.setOnLoadMoreListener {
            pageNum++
            getMessageList(false)
        }

        // 3`接口获取信息
        getMessageList(true)
    }

    var data = mutableListOf<NewsEntity>()

    @SuppressLint("CheckResult")
    private fun getMessageList(isRefresh: Boolean) {

        RetrofitApi.config(requireContext())
            .getNewsList(pageNum, 5)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                Log.d(TAG, "getMessageList: ${it.data}")

                if (it.code == 1) {
                    val list = it.data
                    if (list != null && list.size > 0) {
                        if (isRefresh) {
                            data = list
                        } else {
                            data.addAll(list)
                        }
                        handler.sendEmptyMessage(0)
                    } else {
                        if (isRefresh) {
                            showToast("暂时无数据")
                        } else {
                            showToast("没有更多数据")
                        }
                    }
                }
            }, {
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                navigateTo(LoginActivity::class.java)
                showToast("news界面失败")
            })


        /*  val map = mutableMapOf<String, Any>()
         OkApi.config("app/news/list", map).getRequest(context, object : CallBack {
             override fun onSuccess(res: String) {
                 if (isRefresh) {
                     vb.refreshLayout.finishRefresh(true)
                 } else {
                     vb.refreshLayout.finishLoadMore(true)
                 }
                 val response = Gson().fromJson(res, NewsResponse::class.java)
                 if (response.code == 1 && response != null) {
                     val list = response.data
                     if (isRefresh) {
                         data = list
                     } else {
                         data.addAll(list)
                     }
                     handler.sendEmptyMessage(0)
                 } else {
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
         }) */
    }
}