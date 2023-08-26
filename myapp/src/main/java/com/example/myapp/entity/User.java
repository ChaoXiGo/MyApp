package com.example.myapp.entity;

public class User {

    @Override
    public String toString() {
        return "User{" +
                "code=" + code +
                ", message=" + message +
                ", data=" + data +
                ", map=" + map +
                '}';
    }

    /**
     * code : 1
     * message : null
     * data : {"id":1,"username":"zhangsan","password":"123","token":"my token"}
     * map : {}
     */

    private Integer code;
    private Object message;
    private DataBean data;
    private MapBean map;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public MapBean getMap() {
        return map;
    }

    public void setMap(MapBean map) {
        this.map = map;
    }



    public static class MapBean {
    }
}
