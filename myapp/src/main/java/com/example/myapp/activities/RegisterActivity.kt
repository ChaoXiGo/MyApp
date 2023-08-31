package com.example.myapp.activities

import android.util.Log
import com.example.myapp.api.Api
import com.example.myapp.api.CallBack
import com.example.myapp.databinding.ActivityRegisterBinding
import com.example.myapp.entity.LoginResponse
import com.google.gson.Gson

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val TAG = "RegisterActivity"
    override fun initBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initData() {

        vb.btnRegister.setOnClickListener{
            val mobile = vb.username.text.trim().toString()
            val password = vb.password.text.trim().toString()
            register(mobile,password)
        }
    }

    private fun register(mobile: String, password: String) {
        if (mobile.isEmpty() || password.isEmpty()) {
            showToast("信息不能为空")
            return
        }
        val params = HashMap<String, String>()
        params["mobile"] = mobile
        params["password"] = password

        Api.config("app/register",params as MutableMap<String, Any>).postRequest(this@RegisterActivity,object : CallBack{
            override fun onSuccess(res: String) {
                Log.d(TAG, "onSuccess: $res")
                val user = Gson().fromJson(res, LoginResponse::class.java)
                if (user.code == 1){
                    showToastSync("注册成功")
                }
            }

            override fun onFailure(t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        /*val call = RetrofitUtil.apiService().register(params)
        call.enqueue(
            object : Callback<String>{
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "onResponse: $response")
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
//                    gson.fromJson<>(t.message,)
                    Log.d(TAG, "onFailure: $t.stackTrace")
                }
            }
        )*/
    }
}