package com.example.mandroid.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("posts/{id}")
    Call<PostBean> getPostById(@Path("id") int id);
}
