package com.fmt.mvi.learn.video.state

import com.fmt.mvi.learn.video.model.VideoModel

/**
 * 定义页面所有状态
 */
sealed class VideoListViewState {

    object LoadingState : VideoListViewState()

    data class RefreshSuccessState(val videoList: List<VideoModel>) : VideoListViewState()

    data class LoadMoreSuccessState(val videoList: List<VideoModel>) : VideoListViewState()

    data class LoadErrorState(val errorMsg: String) : VideoListViewState()

    data class LoadMoreErrorState(val errorMsg: String) : VideoListViewState()

}
