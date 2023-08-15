package com.example.retrofit.hilt注入网络模块

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    fun getInfo():Call<String>
}