package com.example.myapp.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {
    @GET("posts/{id}")
    fun retrofitInfo(@Path("id") id: Int): Call<String>

    // @POST("api/login")
    // fun loginUser(@Body request: LoginRequest): Observable<User>
    @POST("app/login")
    fun loginUser(@Body request: HashMap<String,String>): Call<String>

    @POST("app/register")
    fun register(@Body request: HashMap<String,String>): Call<String>

}