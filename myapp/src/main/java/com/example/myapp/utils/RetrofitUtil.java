package com.example.myapp.utils;

import com.example.myapp.api.ApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitUtil {
    private RetrofitUtil() {
    }

    static Retrofit retrofit;
    // static String BASE_URL = "https://jsonplaceholder.typicode.com/";
    // static String BASE_URL = "https://reqres.in/";
    // static String BASE_URL = "http://n4h9yq.natappfree.cc/";
    static String BASE_URL = "http://192.168.1.8:8081/";

    static {
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
    }

    public static ApiService apiService(){
        return retrofit.create(ApiService.class);
    }
}
