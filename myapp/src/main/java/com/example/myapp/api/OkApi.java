package com.example.myapp.api;

import static android.content.Context.MODE_PRIVATE;

import static com.example.myapp.api.ApiConfig.BASE_URl;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.activities.LoginActivity;
import com.example.myapp.utils.StringUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkApi {
    private static OkHttpClient client;
    private static String requestUrl;
    private static Map<String, Object> mParams;
    public static OkApi okApi = new OkApi();

    public OkApi() {

    }

    public static OkApi config(String url, Map<String, Object> params) {

        client = new OkHttpClient.Builder()
                .callTimeout(1000,MILLISECONDS)
                .build();
        requestUrl = BASE_URl + url;
        mParams = params;
        return okApi;
    }

    public void postRequest(Context context, CallBack callback) {
        SharedPreferences sp = context.getSharedPreferences("myapp", MODE_PRIVATE);
        String token = sp.getString("token", "");
        JSONObject jsonObject = new JSONObject(mParams);
        String jsonStr = jsonObject.toString();
        RequestBody requestBodyJson =
                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
                        , jsonStr);

        //第三步创建Request
        Request request = new Request.Builder()
                .url(requestUrl)
                .addHeader("contentType", "application/json;charset=UTF-8")
                .addHeader("token", token)
                .post(requestBodyJson)
                .build();
        //第四步创建call回调对象
        final Call call = client.newCall(request);
        //第五步发起请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", e.getMessage());
                callback.onFailure(e);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                int code = response.code();
                if (code == 401) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    Looper.prepare();
                    Toast.makeText(context, "登录已过期", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    String newToken = response.header("token");
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("token" , newToken);
                    edit.apply();
                    callback.onSuccess(result);
                }
            }
        });
    }

    public void getRequest(Context context, final CallBack callback) {
        SharedPreferences sp = context.getSharedPreferences("myapp", MODE_PRIVATE);
        String token = sp.getString("token", "");
        String url = getAppendUrl(requestUrl, mParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("token", token)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure","异常");
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                int code = response.code();
                if (code == 401) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    Looper.prepare();
                    Toast.makeText(context, "登录已过期", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    String newToken = response.header("token");
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("token" , newToken);
                    edit.apply();
                    callback.onSuccess(result);
                }
            }
        });
    }
    private String getAppendUrl(String url, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            Iterator<Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, Object> entry = iterator.next();
                if (StringUtils.isEmpty(buffer.toString())) {
                    buffer.append("?");
                } else {
                    buffer.append("&");
                }
                buffer.append(entry.getKey()).append("=").append(entry.getValue());
            }
            url += buffer.toString();
        }
        return url;
    }
}

