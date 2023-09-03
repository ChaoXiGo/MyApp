package com.example.myapp.activities

import android.content.Intent
import android.view.View
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    private val TAG = "MainActivity"
    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
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