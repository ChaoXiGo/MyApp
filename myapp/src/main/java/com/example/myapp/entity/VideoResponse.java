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
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "VideoResponse{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        @SerializedName("records")
        private List<DataBean.VideoEntity> videoEntity;
        @SerializedName("total")
        private int total;
        @SerializedName("size")
        private int size;
        @SerializedName("current")
        private int current;
        @SerializedName("orders")
        private List<?> orders;
        @SerializedName("optimizeCountSql")
        private boolean optimizeCountSql;
        @SerializedName("searchCount")
        private boolean searchCount;
        @SerializedName("countId")
        private Object countId;
        @SerializedName("maxLimit")
        private Object maxLimit;
        @SerializedName("pages")
        private int pages;

        public List<DataBean.VideoEntity> getVideoEntity() {
            return videoEntity;
        }

        public void setVideoEntity(List<DataBean.VideoEntity> VideoEntity) {
            this.videoEntity = VideoEntity;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getCurrent() {
            return current;
        }

        public void setCurrent(int current) {
            this.current = current;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public boolean isOptimizeCountSql() {
            return optimizeCountSql;
        }

        public void setOptimizeCountSql(boolean optimizeCountSql) {
            this.optimizeCountSql = optimizeCountSql;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public Object getCountId() {
            return countId;
        }

        public void setCountId(Object countId) {
            this.countId = countId;
        }

        public Object getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Object maxLimit) {
            this.maxLimit = maxLimit;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "videoEntity=" + videoEntity +
                    ", total=" + total +
                    ", size=" + size +
                    ", current=" + current +
                    ", orders=" + orders +
                    ", optimizeCountSql=" + optimizeCountSql +
                    ", searchCount=" + searchCount +
                    ", countId=" + countId +
                    ", maxLimit=" + maxLimit +
                    ", pages=" + pages +
                    '}';
        }

        public static class VideoEntity implements Serializable {
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            @SerializedName("collectState")
            private int collectState;
            @SerializedName("likeState")
            private int likeState;

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

            public int getCollectState() {
                return collectState;
            }

            public void setCollectState(int collectState) {
                this.collectState = collectState;
            }

            public int getLikeState() {
                return likeState;
            }

            public void setLikeState(int likeState) {
                this.likeState = likeState;
            }

            @Override
            public String toString() {
                return "VideoEntity{" +
                        "vid=" + vid +
                        ", vtitle='" + vtitle + '\'' +
                        ", author='" + author + '\'' +
                        ", coverurl='" + coverurl + '\'' +
                        ", headurl='" + headurl + '\'' +
                        ", commentNum=" + commentNum +
                        ", likeNum=" + likeNum +
                        ", collectNum=" + collectNum +
                        ", playurl='" + playurl + '\'' +
                        ", collectState=" + collectState +
                        ", likeState=" + likeState +
                        '}';
            }
        }
    }
}
