package com.example.myapp.activities

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.myapp.R
import com.example.myapp.adapter.MyPagerAdapter
import com.example.myapp.databinding.ActivityHomeBinding
import com.example.myapp.entity.TabEntity
import com.example.myapp.fragment.HomeFragment
import com.example.myapp.fragment.NewsFragment
import com.example.myapp.fragment.MyFragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val mTitles = arrayOf("首页", "资讯", "我的")
    private val mIconUnselectIds = intArrayOf(
        R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
        R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect
    )
    private val mIconSelectIds = intArrayOf(
        R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
        R.mipmap.tab_contact_select, R.mipmap.tab_more_select
    )
    // 存放fragment集合
    private val mFragments = ArrayList<Fragment>()
    // 存放底部按钮的集合
    private var mTabEntities = java.util.ArrayList<CustomTabEntity>()

    override fun initBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun initData() {
        mFragments.add(HomeFragment.newInstance())
        mFragments.add(NewsFragment.newInstance())
        mFragments.add(MyFragment.newInstance())
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }

        initListen()
        // 将底部按钮添加到组件管理器中
        vb.commonTab.setTabData(mTabEntities)
        vb.viewPage.offscreenPageLimit = mFragments.size

        // 适配器交互渲染fragment
        vb.viewPage.adapter = MyPagerAdapter(supportFragmentManager, mTitles, mFragments)
    }

    private fun initListen() {
        vb.commonTab.setOnTabSelectListener(object : OnTabSelectListener{
            override fun onTabSelect(position: Int) {
                vb.viewPage.currentItem = position
            }
            override fun onTabReselect(position: Int) {
            }
        })

        vb.viewPage.setOnPageChangeListener(object : OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }
            override fun onPageSelected(position: Int) {
                vb.commonTab.currentTab = position
            }
            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}