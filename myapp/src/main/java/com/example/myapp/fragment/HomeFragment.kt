package com.example.myapp.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.adapter.HomeAdapter
import com.example.myapp.api.OkApi
import com.example.myapp.api.ApiConfig.VIDEO_CATEGORY_LIST
import com.example.myapp.api.CallBack
import com.example.myapp.databinding.FragmentHomeBinding
import com.example.myapp.entity.VideoCategoryEntity
import com.google.gson.Gson

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {}
    }

    var mFragments: ArrayList<Fragment> = ArrayList()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initData() {

        getVideoCategoryList()

    }

    lateinit var mTitles: Array<String>
    private fun getVideoCategoryList() {
        OkApi.config(VIDEO_CATEGORY_LIST, hashMapOf()).getRequest(context, object : CallBack {
            override fun onSuccess(res: String) {
                activity?.runOnUiThread {
                    kotlin.run {
                        val response = Gson().fromJson(res, VideoCategoryEntity::class.java)
                        if (response != null && response.code == 1) {
                            val list = response.data
                            if (list != null && list.size > 0) {
                                // 初始化字符串数组
                                mTitles = Array(list.size) { "" }
                                for (i in 0 until list.size) {
                                    mTitles[i] = list[i].categoryName
                                    mFragments.add(VideoFragment.instance(list[i].categoryId))
                                }
                            }
                            vb.vp1.offscreenPageLimit = mFragments.size
                            // 每个tab为一个fragment
                            vb.vp1.adapter = HomeAdapter(parentFragmentManager, mTitles, mFragments)
                            vb.SlidingTabLayout.setViewPager(vb.vp1)
                        }
                    }
                }
            }
            override fun onFailure(t: Throwable) {
            }
        })
    }


}