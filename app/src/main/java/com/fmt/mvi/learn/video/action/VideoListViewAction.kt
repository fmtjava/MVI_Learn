package com.fmt.mvi.learn.video.action

/**
 * 定义所有用户操作
 */
sealed class VideoListViewAction {

    object Refresh : VideoListViewAction()

    object Retry : VideoListViewAction()

    data class LoadMore(val page: Int) : VideoListViewAction()
}
