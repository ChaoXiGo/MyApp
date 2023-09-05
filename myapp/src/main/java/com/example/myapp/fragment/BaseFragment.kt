package com.example.myapp.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

    var currentToast: Toast? = null
    fun showToast(message: String) {
        currentToast?.cancel() // 关闭当前的 Toast
        currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        currentToast?.show()
    }

    open fun showToastSync(msg: String?) {
        Looper.prepare()
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
        Looper.loop()
    }

    fun saveSp(key: String, value: String) {
        val sharedPreferences = activity?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        val edit = sharedPreferences?.edit()
        edit?.putString(key, value)
        edit?.apply()
    }

    fun getSp(key: String): String? {
        val sp = activity?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        return sp?.getString(key, "")
    }

    fun removeSp(key:String){
        val sp = activity?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        val edit:SharedPreferences.Editor? = sp?.edit()
        edit?.remove(key)
        edit?.apply()
    }
    fun navigateTo(className:Class<*>?){
        startActivity(Intent(activity,className))
    }

    /**
     * 跳转页面并选择任务栈格式
     */
    fun navigateToWithFlag(className:Class<*>?, flags:Int){
        val intent = Intent(activity, className)
        intent.flags = flags
        startActivity(intent)
    }
}