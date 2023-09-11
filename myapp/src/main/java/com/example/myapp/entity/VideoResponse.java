package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VideoResponse {

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

    public static class VideoEntity implements Serializable{
        @SerializedName("vid")
        private int vid;
        @SerializedName("vtitle")
        private String vtitle;
        @SerializedName("author")
        private String author;
        @SerializedName("coverurl")
        private String coverurl;
        @SerializedName("headurl")
        private String headurl;
        @SerializedName("commentNum")
        private int commentNum;
        @SerializedName("likeNum")
        private int likeNum;
        @SerializedName("collectNum")
        private int collectNum;
        @SerializedName("playurl")
        private String playurl;
        @SerializedName("createTime")
        private String createTime;
        @SerializedName("updateTime")
        private String updateTime;
        @SerializedName("categoryId")
        private int categoryId;
        @SerializedName("categoryName")
        private Object categoryName;
        @SerializedName("videoSocialEntity")
        private Object videoSocialEntity;

        public int getVid() {
            return vid;
        }

        public void setVid(int vid) {
            this.vid = vid;
        }

        public String getVtitle() {
            return vtitle;
        }

        public void setVtitle(String vtitle) {
            this.vtitle = vtitle;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getCoverurl() {
            return coverurl;
        }

        public void setCoverurl(String coverurl) {
            this.coverurl = coverurl;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(int collectNum) {
            this.collectNum = collectNum;
        }

        public String getPlayurl() {
            return playurl;
        }

        public void setPlayurl(String playurl) {
            this.playurl = playurl;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public Object getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(Object categoryName) {
            this.categoryName = categoryName;
        }

        public Object getVideoSocialEntity() {
            return videoSocialEntity;
        }

        public void setVideoSocialEntity(Object videoSocialEntity) {
            this.videoSocialEntity = videoSocialEntity;
        }
    }
}
