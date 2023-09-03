package com.example.myapp.api

object ApiConfig {
    const val PAGE_SIZE = 5
    const val BASE_URl = "http://192.168.1.8:8080/"
    const val LOGIN = "app/login" // 登录
    const val REGISTER = "app/register" // 注册
    const val VIDEO_LIST_ALL = "app/videolist/listAll" // 所有类型视频列表
    const val VIDEO_LIST_BY_CATEGORY = "app/videolist/getListByCategoryId" // 各类型视频列表
    const val VIDEO_CATEGORY_LIST = "app/videocategory/list" // 视频类型列表
    const val NEWS_LIST = "app/news/api/list" // 资讯列表
    const val VIDEO_UPDATE_COUNT = "app/videolist/updateCount" // 更新点赞,收藏,评论
    const val VIDEO_MYCOLLECT = "app/videolist/mycollect" // 我的收藏
}
