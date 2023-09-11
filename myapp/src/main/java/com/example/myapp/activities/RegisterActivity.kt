package com.example.myapp.activities

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnFocusChangeListener
import android.view.View.VISIBLE
import com.example.myapp.api.ApiConfig.REGISTER
import com.example.myapp.api.OkApi
import com.example.myapp.api.CallBack
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.ActivityRegisterBinding
import com.example.myapp.entity.LoginResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val TAG = "RegisterActivity"
    override fun initBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initData() {
        initListen()
    }

    private fun initListen() {
        vb.btnRegister.setOnClickListener {
            val mobile = vb.etUsername.text.trim().toString()
            val password = vb.etPassword.text.trim().toString()
            register(mobile, password)
        }
        vb.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val length = vb.etUsername.text.toString().trim().length
                if (length > 8 || length < 3) vb.tvHint.visibility =
                    VISIBLE else vb.tvHint.visibility = GONE
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun register(mobile: String, password: String) {
        if (mobile.isEmpty() || password.isEmpty()) {
            showToast("信息不能为空")
            return
        }
        val params = mutableMapOf<String, Any>()
        params["mobile"] = mobile
        params["password"] = password


        RetrofitApi.config(this)
            .register(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(TAG, "register: ${it.toString()}")
                if (it.code == 1){
                    showToast("注册成功")
                }else{
                    showToast("用户名已存在")
                }
            },{
                showToast("检查网络连接")
            })

        /* OkApi.config(REGISTER,params).postRequest(this@RegisterActivity,object : CallBack{
            override fun onSuccess(res: String) {
                Log.d(TAG, "onSuccess: $res")
                val response = Gson().fromJson(res, LoginResponse::class.java)
                if (response.code == 1){
                    showToastSync("注册成功")
                }else{
                    showToastSync("用户名已存在")
                }
            }

            override fun onFailure(t: Throwable) {
                TODO("Not yet implemented")
            }
        }) */

    }
}