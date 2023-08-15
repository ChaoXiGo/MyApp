package com.example.myapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myapp.R
import com.example.myapp.adapter.HomeAdapter
import com.flyco.tablayout.SlidingTabLayout

class HomeFragment : BaseFragment() {

    var mFragments: ArrayList<Fragment> = ArrayList()
    lateinit var homeAdapter: HomeAdapter

    lateinit var viewPager: ViewPager
    lateinit var slidingTabLayout: SlidingTabLayout

    override fun initLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        viewPager = mView.findViewById(R.id.vp1)
        slidingTabLayout = mView.findViewById(R.id.SlidingTabLayout)
    }

    override fun initData() {
        val mTitles = arrayOf(
            "热门", "iOS", "Android", "前端", "后端", "设计", "工具资源"
        )
        for (i in mTitles.indices) {
            mFragments.add(VideoFragment.instance())

        }
        homeAdapter = HomeAdapter(parentFragmentManager,mTitles,mFragments)
        viewPager.offscreenPageLimit = mFragments.size
        viewPager.adapter = homeAdapter

        slidingTabLayout.setViewPager(viewPager)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {

            }
    }
}