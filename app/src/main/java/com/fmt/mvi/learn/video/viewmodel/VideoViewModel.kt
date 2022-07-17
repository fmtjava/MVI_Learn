package com.fmt.mvi.learn.video.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmt.mvi.learn.net.ApiService
import com.fmt.mvi.learn.video.action.VideoListViewAction
import com.fmt.mvi.learn.video.state.VideoListViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {

    private val _state = MutableStateFlow<VideoListViewState>(VideoListViewState.LoadingState)
    val state: StateFlow<VideoListViewState>
        get() = _state

    private val userIntent = MutableSharedFlow<VideoListViewAction>()

    init {
        //处理用户事件
        viewModelScope.launch {
            userIntent.collect {
                when (it) {
                    is VideoListViewAction.Refresh -> refresh()
                    is VideoListViewAction.Retry -> retry()
                    is VideoListViewAction.LoadMore -> loadMore(it.page)
                }
            }
        }
    }

    private fun refresh() {
        getVideoList()
    }

    private fun retry() {
        _state.value = VideoListViewState.LoadingState
        getVideoList()
    }

    private fun loadMore(page: Int) {
        getVideoList(page)
    }

    private fun getVideoList(page: Int = 0) {
        viewModelScope.launch {
            kotlin.runCatching {
                ApiService.getVideoList(page = page)
            }.onSuccess {
                _state.value =
                    if (page == 0)
                        VideoListViewState.RefreshSuccessState(it.result.list)
                    else
                        VideoListViewState.LoadMoreSuccessState(it.result.list)
            }.onFailure {
                _state.value =
                    if (page == 0)
                        VideoListViewState.LoadErrorState(it.message.toString())
                    else
                        VideoListViewState.LoadMoreErrorState(it.message.toString())
            }
        }
    }

    /**
     * 分发用户事件
     */
    fun dispatch(viewAction: VideoListViewAction) {
        viewModelScope.launch {
            userIntent.emit(viewAction)
        }
    }
}