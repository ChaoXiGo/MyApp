package com.example.myapp.api

interface CallBack {
    fun onSuccess(res:String)
    fun onFailure(t:Throwable)
}