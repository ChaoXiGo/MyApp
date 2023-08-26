package com.example.myapp.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.adapter.VideoAdapter
import com.example.myapp.entity.DataBean
import com.example.myapp.entity.VideoUser

class VideoFragment : BaseFragment() {

    companion object{
        fun instance(): VideoFragment {
            return VideoFragment().apply {
            }
        }
    }
    private lateinit var recyclerView:RecyclerView

    override fun initLayout(): Int {
        return R.layout.fragment_video
    }

    override fun initView() {
        recyclerView = mView.findViewById(R.id.recyclerView)
    }

    override fun initData() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        val videoUsers:ArrayList<VideoUser> = ArrayList()
        for (i in 0 until 10 ){
            videoUsers.add(VideoUser(i,"username$i","password$i"))
        }
        // 设置适配器给recyclerView
        recyclerView.adapter = VideoAdapter(requireContext(), videoUsers)
    }

    /* fun getListInfo():List<DataBean>{

    } */
}