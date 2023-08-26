package com.example.myapp.activities

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.myapp.R
import com.example.myapp.adapter.MyPagerAdapter
import com.example.myapp.entity.TabEntity
import com.example.myapp.fragment.HomeFragment
import com.example.myapp.fragment.MessageFragment
import com.example.myapp.fragment.MyFragment
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

class HomeActivity : BaseActivity(), OnTabSelectListener, ViewPager.OnPageChangeListener {
    private val mTitles = arrayOf("首页", "消息", "我的")
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

    // 页面管理view
    lateinit var viewPage: ViewPager

    // 底部按钮
    lateinit var commonTabLayout: CommonTabLayout

    override fun initLayout(): Int {
        return R.layout.activity_home
    }

    override fun initView() {
        viewPage = findViewById(R.id.view_page)
        commonTabLayout = findViewById(R.id.common_tab)
        println("修改了")
    }

    override fun initData() {
        mFragments.add(HomeFragment.newInstance())
        mFragments.add(MessageFragment.newInstance())
        mFragments.add(MyFragment.newInstance())

        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        // 将底部按钮添加到组件管理器中
        commonTabLayout.setTabData(mTabEntities)
        commonTabLayout.setOnTabSelectListener(this)
        viewPage.offscreenPageLimit = mFragments.size

        // 适配器交互渲染fragment
        viewPage.adapter = MyPagerAdapter(supportFragmentManager, mTitles, mFragments)
        viewPage.setOnPageChangeListener(this)

    }

    override fun onTabSelect(position: Int) {
        viewPage.currentItem = position
    }

    override fun onTabReselect(position: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        commonTabLayout.currentTab = position
    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}