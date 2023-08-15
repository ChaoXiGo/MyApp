package com.example.myapp.api

import com.example.myapp.entity.LoginRequest
import com.example.myapp.entity.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    @GET("posts/{id}")
    fun getInfo(@Header("Authorization") token: String,
                @Body requestData: LoginRequest): Observable<User>

    @GET("posts/{id}")
    fun retrofitInfo(@Path("id") id: Int): Call<String>

    // @POST("api/login")
    // fun loginUser(@Body request: LoginRequest): Observable<User>
    @POST("api/login")
    fun loginUser(@Body request: LoginRequest): Call<User>
}