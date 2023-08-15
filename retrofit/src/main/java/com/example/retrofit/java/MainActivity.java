// package com.example.retrofit.java;
//
// import android.os.Bundle;
// import android.util.Log;
//
// import androidx.appcompat.app.AppCompatActivity;
//
// import com.example.retrofit.R;
// import com.example.retrofit.bean.User;
//
// import javax.inject.Inject;
//
// import dagger.hilt.android.AndroidEntryPoint;
// import retrofit2.Call;
// import retrofit2.Callback;
// import retrofit2.Response;
//
// @AndroidEntryPoint
// public class MainActivity extends AppCompatActivity {
//
//     private static final String TAG = "MainActivity";
//     // 高版本android访问不加密url需要添加xml文件
//     final String BASE_URL = "http://192.168.101.62:8081/";
//
//     @Inject
//     ApiService apiService;
//
//
//     // final String BASE_URL = "https://jsonplaceholder.typicode.com/";
//     @Override
//     protected void onCreate(Bundle savedInstanceState) {
//         super.onCreate(savedInstanceState);
//         setContentView(R.layout.activity_main);
//
//         Call<User> call = apiService.getInfo(1);
//         call.enqueue(new Callback<User>() {
//             @Override
//             public void onResponse(Call<User> call, Response<User> response) {
//                 Log.d(TAG, "onResponse: "+response.body());
//             }
//
//             @Override
//             public void onFailure(Call<User> call, Throwable t) {
//
//             }
//         });
//
//       /*   Retrofit retrofit = new Retrofit.Builder()
//                 .baseUrl(BASE_URL)
//                 .addConverterFactory(GsonConverterFactory.create())
//                 .build();
//
//         // 代理生成
//         ApiService apiServer = retrofit.create(ApiService.class);
//         // get请求Call
//         // Call<User> call = apiServer.getInfo(1);
//         // Call<String> callName = apiServer.getName();
//
//         //  post请求Call
//         // Call<String> postCall = apiServer.postInfo("提交的数据朝西");
//         User<Object> user = new User<>(12,351,"什么都行","Body内容");
//         Call<User> userCall = apiServer.postUser(user);
//         userCall.enqueue(new Callback<User>() {
//             @Override
//             public void onResponse(Call<User> call, Response<User> response) {
//                 if (response.isSuccessful()){
//                     User newUser = response.body();
//                     Log.d(TAG, "onResponse: "+newUser.getBody());
//                 }
//             }
//
//             @Override
//             public void onFailure(Call<User> call, Throwable t) {
//
//             }
//         });
//  */
//
//     }
// }