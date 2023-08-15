package com.example.myapp.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager

class CustomViewPager : ViewPager {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}