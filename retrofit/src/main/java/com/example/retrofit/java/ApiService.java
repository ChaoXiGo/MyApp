// package com.example.retrofit.java;
//
// import com.example.retrofit.bean.User;
//
// import retrofit2.Call;
// import retrofit2.http.Body;
// import retrofit2.http.GET;
// import retrofit2.http.POST;
// import retrofit2.http.Path;
//
// public interface ApiService {
//     /**
//      * 发送get请求,获取对应id信息
//      * @param id
//      * @return
//      */
//     @GET("posts/{id}")
//     Call<User> getInfo(@Path("id") int id);
//
//     @GET("api/info")
//     Call<String> getName();
//
//     @POST("api/post")
//     Call<String> postInfo(@Body String info);
//
//     @POST("api/post")
//     Call<User> postUser(@Body User user);
// }
