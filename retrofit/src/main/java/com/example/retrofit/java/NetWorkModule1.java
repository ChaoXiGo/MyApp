// package com.example.retrofit.java;
//
// import javax.inject.Singleton;
//
// import dagger.Module;
// import dagger.Provides;
// import dagger.hilt.InstallIn;
// import dagger.hilt.components.SingletonComponent;
// import retrofit2.Retrofit;
// import retrofit2.converter.gson.GsonConverterFactory;
//
// @InstallIn(SingletonComponent.class)
// @Module
// public class NetWorkModule1 {
//
//     @Singleton
//     @Provides
//     Retrofit provideRetrofitClient(){
//         return new Retrofit.Builder()
//                 .baseUrl("https://jsonplaceholder.typicode.com/")
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .build();
//     }
//
//     @Singleton
//     @Provides
//     ApiService provideApiService(Retrofit client){
//         return client.create(ApiService.class);
//     }
// }
