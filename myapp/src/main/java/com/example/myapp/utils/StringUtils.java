package com.example.myapp.utils;

public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() <= 0) {
            return true;
        } else {
            return false;
        }
    }
}
