package com.example.myapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!::mView.isInitialized) { // 检查是否已经初始化
            mView = inflater.inflate(initLayout(), container, false)
            initView()
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    abstract fun initLayout(): Int
    abstract fun initView()
    abstract fun initData()
}