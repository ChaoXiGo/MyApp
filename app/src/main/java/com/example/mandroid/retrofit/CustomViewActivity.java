package com.example.mandroid.retrofit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mandroid.dynamic_proxy.dao.SomeDao;
import com.example.mandroid.dynamic_proxy.dao.impl.SomeDaoImpl;
import com.example.mandroid.dynamic_proxy.proxy.MyProxy;
import com.google.gson.Gson;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomViewActivity extends AppCompatActivity implements View.OnClickListener {
    final static String TAG = "CustomViewActivity";

    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建一个LinearLayout根布局
        LinearLayout mRootLayout = new LinearLayout(this);
        // 设置根布局宽高
        mRootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        // 设置垂直布局
        mRootLayout.setOrientation(LinearLayout.VERTICAL);

        // 创建一个ButtonView, 设置属性
        Button btn1 = new Button(this);
        btn1.setText("dynamic create btn");
        btn1.setTextSize(20);
        mRootLayout.addView(btn1);
        setContentView(mRootLayout);
        btn1.setOnClickListener(this);

        final String BASE_URL = "https://jsonplaceholder.typicode.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);

        SomeDao someDao = new SomeDaoImpl();
        MyProxy myProxy = new MyProxy(someDao);
        SomeDao dao = myProxy.create(SomeDao.class);
        dao.myMethod();
    }

    @Override
    public void onClick(View view) {
        Call<PostBean> call = retrofitInterface.getPostById(1);
        call.enqueue(new Callback<PostBean>() {
            @Override
            public void onResponse(Call<PostBean> call, Response<PostBean> response) {
                if (response.isSuccessful()) {
                    PostBean postBean = response.body();
                    Log.d(TAG, "onResponse: " + postBean);
                }
            }

            @Override
            public void onFailure(Call<PostBean> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
            }
        });
    }
}