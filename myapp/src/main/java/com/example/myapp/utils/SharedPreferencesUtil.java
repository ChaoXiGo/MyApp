package com.example.myapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {
    private static final String PREFS_NAME = "myapp";

    public static void saveString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

}
