package com.example.mandroid;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mandroid.connectionpool.ConnectionPoll;
import com.example.mandroid.connectionpool.DatabaseConnection;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestActivity extends AppCompatActivity {
    final static String TAG =  "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout mLayout = new LinearLayout(this);
        setContentView(mLayout);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        TextView tv1 = new TextView(this);
        tv1.setText("Custom textView");
        tv1.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv1.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mLayout.addView(tv1);
        tv1.setOnClickListener(v->{
            ConnectionPoll poll = new ConnectionPoll(3);
            DatabaseConnection connection = poll.getConnection();
            connection.query("connection" + connection);
            poll.releaseConnection(connection);

        });

        // 1`创建client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .cookieJar(CookieJar.NO_COOKIES)
                .callTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        // 2`创建Request
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        // 3`构建Call对象
        Call call = client.newCall(request);
        // 4`调用call对象的同步请求方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: failure");

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()){
                    String result = response.body().string();
                    Log.d(TAG, "onResponse: success" + result);
                }
            }
        });
    }
}
class HandlerThread extends Thread {
    Looper looper;

    public Looper getLooper() {
        return looper;
    }

    @Override
    public void run() {
        super.run();
        Looper.prepare();
        looper = Looper.myLooper();
        Looper.loop();
    }

    @Override
    public synchronized void start() {
        super.start();
    }
}