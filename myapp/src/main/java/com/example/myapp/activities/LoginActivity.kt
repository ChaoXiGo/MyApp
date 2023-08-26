package com.example.myapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.R
import com.example.myapp.entity.LoginRequest
import com.example.myapp.entity.User
import com.example.myapp.utils.RetrofitUtil
import com.example.myapp.utils.SharedPreferencesUtil
import io.reactivex.rxjava3.functions.Consumer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : BaseActivity(), View.OnClickListener {

    private val TAG = "LoginActivity"
    lateinit var et_username: TextView
    lateinit var et_password: TextView
    lateinit var btnLogin: Button
    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        btnLogin = findViewById(R.id.btn_login)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
    }

    override fun initData() {
        btnLogin.setOnClickListener(this)

    }

    @SuppressLint("CheckResult")
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                login()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val account = SharedPreferencesUtil.getString(this, "account", "")
        val password = SharedPreferencesUtil.getString(this, "password", "")
        et_username.text = account
        et_password.text = password
    }

    @SuppressLint("CheckResult")
    fun login() {
        val account = et_username.text.trim().toString()
        val password = et_password.text.trim().toString()
        if (account.isEmpty() || password.isEmpty()) {
            Toast.makeText(this@LoginActivity, "信息不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val call =
            RetrofitUtil.apiService().loginUser(LoginRequest(account, password))
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()
                if (user?.code == 1){
                    Log.d(TAG, "onResponse: $user")
                    val token = user.data.token
                    SharedPreferencesUtil.saveString(this@LoginActivity,"token",token)
                    SharedPreferencesUtil.saveString(this@LoginActivity,"account",account)
                    SharedPreferencesUtil.saveString(this@LoginActivity,"password",password)
                    val currentToken = SharedPreferencesUtil.getString(this@LoginActivity, "token", "")
                    Toast.makeText(this@LoginActivity, "登录成功$currentToken", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                }else{
                    Log.d(TAG, "onFailure: 登录失败")
                }

            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }

        })

    }

}