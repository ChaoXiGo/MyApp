package com.example.myapp.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.myapp.activities.LoginActivity
import com.example.myapp.activities.MyCollectActivity
import com.example.myapp.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() =
            MyFragment().apply {
            }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        initListen()
    }

    private fun initListen() {
        vb.llCollect
            .setOnClickListener {
            navigateTo(MyCollectActivity::class.java)
        }
        vb.llLogout.setOnClickListener {
            removeSp("token")
            navigateToWithFlag(
                LoginActivity::class.java,
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            )
        }
        vb.llSkin.setOnClickListener {}

    }
}