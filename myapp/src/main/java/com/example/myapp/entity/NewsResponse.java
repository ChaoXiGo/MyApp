package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private Object message;
    @SerializedName("data")
    private List<NewsEntity> data;

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

    public List<NewsEntity> getData() {
        return data;
    }

    public void setData(List<NewsEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
