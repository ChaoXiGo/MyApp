package com.example.retrofit.bean;

public class User<T> {
    private int userId;
    private int id;
    private T title;
    private String body;

    public User() {
    }

    public User(int userId, int id, T title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = (T) title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public T getTitle() {
        return title;
    }

    public void setTitle(T title) {
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
        return "User{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
