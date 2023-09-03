package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoCategoryEntity {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private Object message;
    @SerializedName("data")
    private List<CategoryEntity> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<CategoryEntity> getData() {
        return data;
    }

    public void setData(List<CategoryEntity> data) {
        this.data = data;
    }
}
