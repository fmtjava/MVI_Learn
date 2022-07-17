package com.fmt.mvi.learn.travel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmt.mvi.learn.net.ApiService
import com.fmt.mvi.learn.travel.action.TravelTabViewAction
import com.fmt.mvi.learn.travel.model.Params
import com.fmt.mvi.learn.travel.state.TravelTabViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TravelTabViewModel : ViewModel() {

    private val _state = MutableSharedFlow<TravelTabViewState>()
    val state: SharedFlow<TravelTabViewState>
        get() = _state

    private val userIntent = MutableSharedFlow<TravelTabViewAction>()

    init {
        viewModelScope.launch {
            userIntent.collect { viewAction ->
                when (viewAction) {
                    is TravelTabViewAction.Refresh -> getTravelCategoryList(
                        viewAction.url,
                        viewAction.param
                    )
                    is TravelTabViewAction.LoadMore -> getTravelCategoryList(
                        viewAction.url,
                        viewAction.param
                    )
                }
            }
        }
    }

    private fun getTravelCategoryList(url: String, params: Params) {
        viewModelScope.launch {
            kotlin.runCatching {
                ApiService.getTravelCategoryList(url, params)
            }.onSuccess {
                _state.emit(
                    if (params.pagePara.pageIndex == 0) TravelTabViewState.RefreshSuccess(it.resultList)
                    else TravelTabViewState.LoadMoreSuccess(it.resultList)
                )
            }.onFailure {
                _state.emit(TravelTabViewState.LoadError(it.message.toString()))
            }
        }
    }

    fun dispatch(viewAction: TravelTabViewAction) {
        viewModelScope.launch {
            userIntent.emit(viewAction)
        }
    }
}