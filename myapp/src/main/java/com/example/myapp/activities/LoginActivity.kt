package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.myapp.MyApplication.TAG
import com.example.myapp.api.RetrofitApi
import com.example.myapp.databinding.ActivityLoginBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun initBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initData() {
        initListen()
    }

    private fun initListen() {
        vb.btnLogin.setOnClickListener {
            val mobile = vb.etUsername.text.trim().toString()
            val password = vb.etPassword.text.trim().toString()
            login(mobile, password)
        }
        vb.etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val length = vb.etUsername.text.toString().trim().length
                if (length > 8 || length < 3) vb.tvHint.visibility =
                    View.VISIBLE else vb.tvHint.visibility = View.GONE
            }
        })

        vb.btnRegister.setOnClickListener {
            navigateTo(RegisterActivity::class.java)
        }

    }

    override fun onResume() {
        super.onResume()
        val mobile = getString("mobile")
        val password = getString("password")
        vb.etUsername.setText(mobile)
        vb.etPassword.setText(password)
    }

    @SuppressLint("CheckResult")
    fun login(account: String, password: String) {
        if (account.isEmpty() || password.isEmpty()) {
            showToast("信息不能为空")
            return
        }
        val params = mutableMapOf<String, Any>()
        params["mobile"] = account
        params["password"] = password
        RetrofitApi.config(this)
            .login(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.code == 1) {
                    saveToSp("mobile", account)
                    saveToSp("password", password)
                    saveToSp("expire", it.data.expire)
                    saveToSp("token", it.data.token)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    showToast("登录成功")
                }
            },
                {
                    showToast("网络超时")
                })

        /*  OkApi.config(LOGIN, params).postRequest(this@LoginActivity,object :CallBack{
                override fun onSuccess(res: String) {
                    Log.d(TAG, "onSuccess: $res")
                    val gson = Gson()
                    val user = gson.fromJson(res, LoginResponse::class.java)
                    if (user.code == 1){
                        saveToSp("mobile",account)
                        saveToSp("password",password)
                        saveToSp("expire",user.data.expire)
                        saveToSp("token",user.data.token)
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        showToastSync("登录成功")
                    }
                }

                override fun onFailure(t: Throwable) {
                    showToastSync("网络连接错误")
                }
            }) */
    }
}