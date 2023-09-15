package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.MyApplication.TAG
import com.example.myapp.R
import com.example.myapp.adapter.MyCollectAdapter
import com.example.myapp.adapter.VideoAdapter
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.ActivityMyCollectBinding
import com.example.myapp.entity.NewsResponse
import com.example.myapp.entity.VideoResponse.DataBean.VideoEntity
import com.example.myapp.linstener.OnItemChildClickListener
import com.example.myapp.linstener.OnItemClickListener
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
import xyz.doikki.videoplayer.player.BaseVideoView
import xyz.doikki.videoplayer.player.VideoView
import xyz.doikki.videoplayer.player.VideoViewManager
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
                // 3`设置当前recycleView的adapter
                vb.recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }

    lateinit var adapter: MyCollectAdapter
    private var pageNum = 1
    /**
     * 当前播放的位置
     */
    var mCurPos = -1

    /**
     * 上次播放的位置，用于页面切回来之后恢复播放
     */
    var mLastPos = mCurPos

    private lateinit var layoutManager:LinearLayoutManager
    override fun initData() {
        // 初始化播放器
        initVideoView()
        // 1`初始化recycleView
        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        vb.recyclerView.layoutManager = layoutManager
        // 2`创建adapter
        adapter = MyCollectAdapter(this)

        vb.recyclerView.addOnChildAttachStateChangeListener(object :
            RecyclerView.OnChildAttachStateChangeListener {
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
            override fun OnItemChildClick(position: Int) {
                startPlay(position)
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

    /**
     * 初始化播放器设置
     */
    lateinit var mVideoView: VideoView
    lateinit var mController: StandardVideoController
    lateinit var mErrorView: ErrorView
    lateinit var mCompleteView: CompleteView
    lateinit var mTitleView: TitleView

    private fun initVideoView() {
        mVideoView = VideoView(this)
        mVideoView.setOnStateChangeListener(object : BaseVideoView.SimpleOnStateChangeListener() {
            override fun onPlayStateChanged(playState: Int) {
                // 监听VideoViewManager释放，重置状态
                if (playState == VideoView.STATE_IDLE) {
                    Utils.removeViewFormParent(mVideoView)
                    mLastPos = mCurPos
                    mCurPos = -1
                }
            }
        })

        mController = StandardVideoController(this)
        mErrorView = ErrorView(this)
        mController.addControlComponent(mErrorView)
        mCompleteView = CompleteView(this)
        mController.addControlComponent(mCompleteView)
        mTitleView = TitleView(this)
        mController.addControlComponent(mTitleView)
        mController.addControlComponent(VodControlView(this))
        mController.addControlComponent(GestureView(this))
        mController.setEnableOrientation(true)
        mVideoView.setVideoController(mController)
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
        val videoEntity = collectList[position]
        mVideoView.setUrl(videoEntity.playurl)
        mTitleView.setTitle(videoEntity.vtitle)
        val itemView: View = layoutManager.findViewByPosition(position) ?: return
        val viewHolder = itemView.tag as MyCollectAdapter.ViewHolder
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
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        mCurPos = -1
    }
}