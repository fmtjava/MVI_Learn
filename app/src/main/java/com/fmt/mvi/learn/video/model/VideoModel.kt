package com.fmt.mvi.learn.video.model

data class VideoListResponseModel(
    val code: Int,
    val message: String,
    val result: VideoListModel
)

data class VideoListModel(
    val total: Int,
    val list: List<VideoModel>
)

data class VideoModel(
    val title: String,
    val userName: String,
    val userPic: String,
    val coverUrl: String,
    val playUrl: String,
    val duration: String,
)