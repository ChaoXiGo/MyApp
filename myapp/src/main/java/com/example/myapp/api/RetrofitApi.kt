package com.example.myapp.api

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitApi {
    companion object {
        private var retrofit: Retrofit? = null
        private const val BASE_URl = "http://192.168.1.6:8080/"
        lateinit var mContext: Context

        fun config(context: Context): ApiService {
            mContext = context
            if (retrofit == null) {

                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val okHttpClient = OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addNetworkInterceptor(TokenHeaderInterceptor())
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit?.create(ApiService::class.java)!!
        }
    }

    class TokenHeaderInterceptor : Interceptor {
        @Override
        override fun intercept(chain: Interceptor.Chain): Response {
            val sp = mContext.getSharedPreferences("myapp", Context.MODE_PRIVATE)
            val token: String? = sp.getString("token", "")
            val request = chain.request()
            // 创建新请求，添加 token 头部
            val newRequest = if (token.isNullOrEmpty()) {
                request
            } else {
                request.newBuilder()
                    .header("token", token)
                    .build()
            }
            return chain.proceed(newRequest)
        }
    }
}