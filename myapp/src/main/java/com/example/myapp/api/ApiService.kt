package com.example.myapp.api

import com.example.myapp.entity.LoginResponse
import com.example.myapp.entity.NewsResponse
import com.example.myapp.entity.VideoCategoryEntity
import com.example.myapp.entity.VideoListEntity
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @POST("app/login")
    fun login(@Body params: MutableMap<String, Any>): Observable<LoginResponse>

    @POST("app/register")
    fun register(@Body params: MutableMap<String, Any>): Observable<LoginResponse>

    @GET("app/videocategory/list")
    fun getVideoCategoryList(): Observable<VideoCategoryEntity>

    @GET("app/videolist/getlistbyid")
    fun getListById(
        @Query("page") page: Int, @Query("limit") limit: Int, @Query("categoryId") categoryId: Int
    ): Observable<VideoListEntity>

    @GET("app/news/list")
    fun getNewsList(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Observable<NewsResponse>

    @POST("app/videolist/updateCount")
    fun updateCount(
        @Query("type") type: Int, @Query("vid") vid: Int, @Query("flag") flag: Boolean
    ): Observable<Unit>
}