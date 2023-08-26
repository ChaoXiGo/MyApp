package com.example.myapp.entity

class DataBean {
    /**
     * id : 1
     * username : zhangsan
     * password : 123
     * token : my token
     */
    var id: Int? = null
    var username: String? = null
    var password: String? = null
    var token: String? = null

    override fun toString(): String {
        return "DataBean{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}'
    }
}