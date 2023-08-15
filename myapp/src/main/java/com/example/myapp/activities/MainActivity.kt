package com.example.myapp.activities

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.myapp.R

class MainActivity : BaseActivity(), View.OnClickListener {
    private val TAG = "MainActivity"

    lateinit var btn_login: Button
    lateinit var btn_register: Button
    override fun initLayout(): Int {
        supportActionBar?.hide()
        return R.layout.activity_main
    }

    override fun initView() {
        btn_login = findViewById(R.id.btn_login)
        btn_register = findViewById(R.id.btn_register)
        btn_register.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_login -> {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }

            R.id.btn_register -> {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
        }
    }
}