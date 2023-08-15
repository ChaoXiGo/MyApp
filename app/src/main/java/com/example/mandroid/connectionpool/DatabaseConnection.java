package com.example.mandroid.connectionpool;

import android.util.Log;

public class DatabaseConnection {
    final String TAG = "DatabaseConnection";
    public void query(String sql){
        Log.d(TAG, "query: " + sql);
    }

    public void close(){
        Log.d(TAG, "DatabaseConnection close");

    }
}
