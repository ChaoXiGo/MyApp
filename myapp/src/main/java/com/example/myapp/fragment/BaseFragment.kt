package com.example.myapp.fragment

import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var vb: T
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = inflateBinding(inflater, container)
        return vb.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    abstract fun initData()

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    open fun showToastSync(msg: String?) {
        Looper.prepare()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        Looper.loop()
    }

    fun saveToSp(key: String, value: String) {
        val sharedPreferences = activity?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(key, value)
        edit?.apply()
    }

    fun getString(key: String): String? {
        val sp = activity?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        return sp?.getString(key, "")
    }
}