package com.example.mandroid.retrofit;

public class PostBean {
    private int id;
    private String title;
    private String body;

    // Getter and setter methods


    public PostBean(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
