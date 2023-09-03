package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoListEntity implements Serializable {

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private Object message;
    @SerializedName("data")
    private List<VideoEntity> data;

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

    public List<VideoEntity> getData() {
        return data;
    }

    public void setData(List<VideoEntity> data) {
        this.data = data;
    }
}
