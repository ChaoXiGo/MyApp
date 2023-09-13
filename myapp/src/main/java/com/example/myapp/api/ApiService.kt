package com.example.myapp.api

import com.example.myapp.entity.VideoResponse
import com.example.myapp.entity.LoginResponse
import com.example.myapp.entity.NewsResponse
import com.example.myapp.entity.VideoCategoryResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @POST("app/login")
    fun login(@Body params: MutableMap<String, Any>): Observable<LoginResponse>

    @POST("app/register")
    fun register(@Body params: MutableMap<String, Any>): Observable<LoginResponse>

    @GET("app/videocategory/list")
    fun getVideoCategoryList(): Observable<VideoCategoryResponse>

    @GET("app/videolist/getlistbyid")
    fun getListById(
        @Query("page") page: Int, @Query("limit") limit: Int, @Query("categoryId") categoryId: Int
    ): Observable<VideoResponse>

    @GET("app/news/list")
    fun getNewsList(
        @Query("page") page: Int, @Query("limit") limit: Int
    ): Observable<NewsResponse>

    @GET("app/updatecount")
    fun updateCount(
        @Query("vid") vid: Int, @Query("type") type: Int,@Query("flag") flag: Boolean
    ): Observable<Unit>

    @GET("app/getcollect")
    fun getCollectList(
        @Query("page") page: Int, @Query("limit") limit: Int
    ) : Observable<VideoResponse>
}