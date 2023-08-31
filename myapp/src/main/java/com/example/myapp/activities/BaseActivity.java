package com.example.myapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity {
    protected T vb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null){
            supportActionBar.hide();
        }
        vb = initBinding();
        setContentView(vb.getRoot());
        initData();
    }

    abstract T initBinding();

    abstract void initData();

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 主线程执行Toast
     */
    public void showToastSync(String message){
        Looper.prepare();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }

    public void saveToSp(String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences("myapp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences("myapp", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}