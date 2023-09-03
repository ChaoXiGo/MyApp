package com.example.myapp.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NewsEntity implements Serializable {
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
    @SerializedName("thumbEntities")
    private List<ThumbEntitiesBean> thumbEntities;
    @SerializedName("imgList")
    private List<String> imgList;

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

    public List<ThumbEntitiesBean> getThumbEntities() {
        return thumbEntities;
    }

    public void setThumbEntities(List<ThumbEntitiesBean> thumbEntities) {
        this.thumbEntities = thumbEntities;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    private class ThumbEntitiesBean {
        private int thumbId;
        private String thumbUrl;
        private int newsId;

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

        public int getNewsId() {
            return newsId;
        }

        public void setNewsId(int newsId) {
            this.newsId = newsId;
        }
    }
}
