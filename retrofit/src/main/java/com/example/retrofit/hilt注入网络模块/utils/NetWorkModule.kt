package com.example.retrofit.hilt注入网络模块.utils

import android.util.Log
import com.example.retrofit.hilt注入网络模块.ApiService
import com.example.retrofit.hilt注入网络模块.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetWorkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Log.d("网络请求", message) }
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            //    kotlin可以不使用RxJava
            // .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}