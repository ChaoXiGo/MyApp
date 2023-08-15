package com.example.myapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 进行信息和Fragment之间的渲染交互
 */
class MyPagerAdapter(fm: FragmentManager,title:Array<String>,fragment:ArrayList<Fragment>) :
    FragmentPagerAdapter(fm) {

    private var mTitles: Array<String> = title
    private var mFragments : ArrayList<Fragment> = fragment

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

}