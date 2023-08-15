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

    public static class DataBean {
        /**
         * id : 1
         * username : zhangsan
         * password : 123
         * token : my token
         */

        private Integer id;
        private String username;
        private String password;
        private String token;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }

    public static class MapBean {
    }
}
