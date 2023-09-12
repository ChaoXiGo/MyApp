package com.example.myapp.fragment

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.adapter.VideoAdapter
import com.example.myapp.entity.VideoResponse.DataBean.VideoEntity
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.FragmentVideoBinding
import com.example.myapp.entity.VideoResponse
import com.example.myapp.linstener.OnItemClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable


/**
 * 每个tab的内容fragment
 */
class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    @SuppressLint("HandlerLeak")
    private val handler: Handler =
        object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == 0) {
                    adapter.setInfo(data)
                    // 设置适配器给recyclerView
                    vb.recyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
        }

    // 当前tab Id
    var categoryId: Int = 0

    // 当前页数
    private var pageNum: Int = 1

    companion object {
        fun instance(categoryId: Int): VideoFragment {
            return VideoFragment().apply {
                this.categoryId = categoryId
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoBinding {
        return FragmentVideoBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        // 1`初始化recyclerView
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager

        adapter = VideoAdapter(requireContext())
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(obj: Serializable) {
                val entity = obj as VideoEntity
                showToast("点击成功$entity")
            }
        })

        // 下拉刷新控件
        vb.refreshLayout.setOnRefreshListener {
            /**
             * 刷新触发，获取信息
             */
            pageNum = 1
            getVideoList(true)
        }

        // 上拉加载
        vb.refreshLayout.setOnLoadMoreListener {
            pageNum++
            getVideoList(false)
        }
        // 接口获取信息
        getVideoList(true)
    }

    lateinit var adapter: VideoAdapter

    var data = mutableListOf<VideoEntity>()

    /**
     * 接口获取数据
     */
    @SuppressLint("NotifyDataSetChanged", "CheckResult")
    fun getVideoList(isRefresh: Boolean) {
        val params = mutableMapOf<String, Any>()
        params["page"] = pageNum
        params["limit"] = 5
        params["categoryId"] = categoryId

        RetrofitApi.config(requireContext())
            .getListById(pageNum, 5, categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                if (it.code == 1){
                    val list = it.data.videoEntity
                    if (list!= null && list.size >0){
                        // 进行刷新动作界面为上， 显示第一页， 否则加载数据添加到集合
                        if (isRefresh){
                            data = list
                        }else{
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
            }, {
                if (isRefresh) {
                    vb.refreshLayout.finishRefresh(true)
                } else {
                    vb.refreshLayout.finishLoadMore(true)
                }
                showToast("请求超时，检查网络连接或服务器断开连接")
            })

        /* OkApi.config("app/videolist/getlistbyid", params).getRequest(context,object :CallBack{
            override fun onSuccess(res: String) {
                if (isRefresh){
                    vb.refreshLayout.finishRefresh(true)
                }else{
                    vb.refreshLayout.finishLoadMore(true)
                }
                val response = Gson().fromJson(res, VideoListEntity::class.java)
                if (response.code == 1){
                    val videoList = response.data
                    if (videoList != null && videoList.size > 0){
                        // 进行刷新动作界面为上， 显示第一页， 否则加载数据添加到集合
                        if (isRefresh){
                            data = videoList
                        }else{
                            data.addAll(videoList)
                        }
                        // 传入参数
                      handler.sendEmptyMessage(0)
                    }else{
                        if (isRefresh) {
                            showToastSync("暂时无数据")
                        } else {
                            showToastSync("没有更多数据")
                        }
                    }
                }
            }

            override fun onFailure(t: Throwable) {
                if (isRefresh){
                    vb.refreshLayout.finishRefresh(true)
                }else{
                    vb.refreshLayout.finishLoadMore(true)
                }
            }
        }) */
    }
}