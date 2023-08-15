package com.example.retrofit.hilt注入网络模块.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.retrofit.R;
import com.example.retrofit.hilt注入网络模块.ApiService;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Inject
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Call<String> call = apiService.getInfo();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG", "onResponse: "+response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}