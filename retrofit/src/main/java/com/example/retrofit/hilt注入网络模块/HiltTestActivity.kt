package com.example.retrofit.hilt注入网络模块

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.retrofit.R
import com.example.retrofit.bean.User
import com.example.retrofit.hilt注入网络模块.utils.NetWorkModule
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HiltTestActivity : AppCompatActivity() {

    lateinit var apiService:ApiService
    private val TAG = "HiltTestActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt_test)

        apiService.getInfo().enqueue(
            object: Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}