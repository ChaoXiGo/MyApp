package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
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

    public static class NewsEntity implements Serializable {
        @SerializedName("newsId")
        private int newsId;
        @SerializedName("newsTitle")
        private String newsTitle;
        @SerializedName("authorName")
        private String authorName;
        @SerializedName("headerUrl")
        private String headerUrl;
        @SerializedName("commentCount")
        private int commentCount;
        @SerializedName("releaseDate")
        private String releaseDate;
        @SerializedName("type")
        private int type;
        @SerializedName("newsThumbList")
        private List<NewsThumbListBean> newsThumbList;

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }

        public String getNewsTitle() {
            return newsTitle;
        }

        public void setNewsTitle(String newsTitle) {
            this.newsTitle = newsTitle;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getHeaderUrl() {
            return headerUrl;
        }

        public void setHeaderUrl(String headerUrl) {
            this.headerUrl = headerUrl;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<NewsThumbListBean> getNewsThumbList() {
            return newsThumbList;
        }

        public void setNewsThumbList(List<NewsThumbListBean> newsThumbList) {
            this.newsThumbList = newsThumbList;
        }

        public static class NewsThumbListBean {
            @SerializedName("newsId")
            private int newsId;
            @SerializedName("thumbId")
            private int thumbId;
            @SerializedName("thumbUrl")
            private String thumbUrl;

            public int getNewsId() {
                return newsId;
            }

            public void setNewsId(int newsId) {
                this.newsId = newsId;
            }

            public int getThumbId() {
                return thumbId;
            }

            public void setThumbId(int thumbId) {
                this.thumbId = thumbId;
            }

            public String getThumbUrl() {
                return thumbUrl;
            }

            public void setThumbUrl(String thumbUrl) {
                this.thumbUrl = thumbUrl;
            }
        }
    }
}
