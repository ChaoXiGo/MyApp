package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoCategoryResponse {

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

    public static class CategoryEntity implements Serializable {
        /**
         * categoryId : 1
         * categoryName : 游戏
         */

        private int categoryId;
        private String categoryName;

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }

}
