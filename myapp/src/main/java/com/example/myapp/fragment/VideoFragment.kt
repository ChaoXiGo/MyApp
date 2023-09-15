package com.example.myapp.fragment

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
import com.example.myapp.MyApplication.TAG
import com.example.myapp.R
import com.example.myapp.adapter.VideoAdapter
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.FragmentVideoBinding
import com.example.myapp.entity.VideoResponse.DataBean.VideoEntity
import com.example.myapp.linstener.OnItemChildClickListener
import com.example.myapp.utils.Tag
import com.example.myapp.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import xyz.doikki.videocontroller.StandardVideoController
import xyz.doikki.videocontroller.component.CompleteView
import xyz.doikki.videocontroller.component.ErrorView
import xyz.doikki.videocontroller.component.GestureView
import xyz.doikki.videocontroller.component.TitleView
import xyz.doikki.videocontroller.component.VodControlView
import xyz.doikki.videoplayer.player.BaseVideoView.SimpleOnStateChangeListener
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.player.VideoViewManager


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

    /**
     * 当前播放的位置
     */
    var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    var mLastPos = mCurPos

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

    private lateinit var layoutManager: LinearLayoutManager
    override fun initData() {
        // 初始化播放器
        initVideoView()

        // 1`初始化recyclerView
        layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager
        adapter = VideoAdapter(requireContext())

        vb.recyclerView.addOnChildAttachStateChangeListener(object :
            OnChildAttachStateChangeListener {
            override fun onChildViewAttachedToWindow(view: View) {
            }

            override fun onChildViewDetachedFromWindow(view: View) {
                val playerContainer = view.findViewById<FrameLayout>(R.id.player_container)
                val v = playerContainer.getChildAt(0)
                if (v != null && v === mVideoView && !mVideoView.isFullScreen) {
                    releaseVideoView()
                }
            }
        })

        adapter.setOnItemClickListener(object : OnItemChildClickListener {
            /*  override fun onItemClick(obj: Serializable) {
                val bundle = Bundle().apply {
                    putSerializable("videoEntity", obj)
                }
                val intent = Intent(requireContext(), WebActivity::class.java).apply {
                    putExtras(bundle)
                }
                 *//* val bundle = Bundle()
                bundle.putSerializable("videoEntity", obj)
                val intent = Intent(this@MyCollectActivity, WebActivity::class.java)
                intent.putExtras(bundle) *//*
                startActivity(intent)
            } */
            override fun OnItemChildClick(position: Int) {
                startPlay(position)
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


    lateinit var mVideoView: VideoView
    lateinit var mController: StandardVideoController
    lateinit var mErrorView: ErrorView
    lateinit var mCompleteView: CompleteView

    lateinit var mTitleView: TitleView

    /**
     * 初始化播放器设置
     */
    private fun initVideoView() {
        mVideoView = VideoView(requireContext())
        mVideoView.setOnStateChangeListener(object : SimpleOnStateChangeListener() {
            override fun onPlayStateChanged(playState: Int) {
                // 监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView)
                    mLastPos = mCurPos
                    mCurPos = -1
                }
            }
        })

        mController = StandardVideoController(requireContext())
        mErrorView = ErrorView(activity)
        mController.addControlComponent(mErrorView)
        mCompleteView = CompleteView(requireActivity())
        mController.addControlComponent(mCompleteView)
        mTitleView = TitleView(requireActivity())
        mController.addControlComponent(mTitleView)
        mController.addControlComponent(VodControlView(requireActivity()))
        mController.addControlComponent(GestureView(requireActivity()))
        mController.setEnableOrientation(true)
        mVideoView.setVideoController(mController)
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
                Log.d(TAG, "getVideoList: $it")
                if (it.code == 1) {
                    val list = it.data.videoEntity
                    if (list != null && list.size > 0) {
                        // 进行刷新动作界面为上， 显示第一页， 否则加载数据添加到集合
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
                showToast("检查网络连接或服务器断开连接")
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

    override fun onPause() {
        super.onPause()
        pause()
    }

    /**
     * 由于onPause必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onPause的逻辑
     */
    private fun pause() {
        releaseVideoView()
    }

    override fun onResume() {
        super.onResume()
        resume()
    }

    /**
     * 由于onResume必须调用super。故增加此方法，
     * 子类将会重写此方法，改变onResume的逻辑
     */
    private fun resume() {
        if (mLastPos == -1) return
        // 恢复上次播放的位置
        startPlay(mLastPos)
    }

    private fun startPlay(position: Int) {
        if (mCurPos == position) return
        if (mCurPos != -1) releaseVideoView()
        val videoEntity = data[position]
        mVideoView.setUrl(videoEntity.playurl)
        mTitleView.setTitle(videoEntity.vtitle)
        val itemView: View = layoutManager.findViewByPosition(position) ?: return
        val viewHolder = itemView.tag as VideoAdapter.ViewHolder
        // 把列表中预置的PrepareView添加到控制器中，注意isPrivate此处只能为true。
        mController.addControlComponent(viewHolder.prepareView,true)
        Utils.removeViewFormParent(mVideoView)
        viewHolder.playerController.addView(mVideoView,0)
        // 播放之前将VideoView添加到VideoViewManager以便在别的页面也能操作它
        VideoViewManager.instance().add(mVideoView, Tag.LIST)
        mVideoView.start()
        mCurPos = position

    }

    private fun releaseVideoView(){
        mVideoView.release()
        if (mVideoView.isFullScreen) mVideoView.stopFullScreen()
        if (requireActivity().requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        mCurPos = -1
    }
}