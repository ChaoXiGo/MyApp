package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import com.example.myapp.api.Api
import com.example.myapp.api.CallBack
import com.example.myapp.databinding.ActivityLoginBinding
import com.example.myapp.entity.LoginResponse
import com.google.gson.Gson

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val TAG = "LoginActivity"
    override fun initBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initData() {
        vb.btnLogin.setOnClickListener {
            val account = vb.etUsername.text.trim().toString()
            val password = vb.etPassword.text.trim().toString()
            login(account, password)
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
        val params = HashMap<String, Any>()
        params["mobile"] = account
        params["password"] = password
        Api.config("app/login", params as Map<String, Any>?).postRequest(this@LoginActivity,object :CallBack{
            override fun onSuccess(res: String) {
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
                TODO("Not yet implemented")
            }
        })
    }
}