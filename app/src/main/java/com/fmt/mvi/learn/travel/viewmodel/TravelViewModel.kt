package com.fmt.mvi.learn.travel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmt.mvi.learn.net.Api
import com.fmt.mvi.learn.travel.state.TravelViewState
import com.fmt.mvi.learn.travel.action.TravelViewAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor(private val mApiService: Api) : ViewModel() {

    private val _state = MutableStateFlow<TravelViewState>(TravelViewState.LoadingState)
    val state: StateFlow<TravelViewState>
        get() = _state

    private val userIntent = MutableSharedFlow<TravelViewAction>()

    init {
        viewModelScope.launch {
            userIntent.collect { viewAction ->
                when (viewAction) {
                    is TravelViewAction.GetTravelTabs -> getTravelTabs()
                    is TravelViewAction.Retry -> retry()
                }
            }
        }
    }

    private fun retry() {
        _state.value = TravelViewState.LoadingState
        getTravelTabs()
    }

    private fun getTravelTabs() {
        viewModelScope.launch {
            kotlin.runCatching {
                mApiService.getTravelTab()
            }.onSuccess {
                _state.value = TravelViewState.LoadSuccess(it)
            }.onFailure {
                _state.value = TravelViewState.LoadFail(it.message.toString())
            }
        }
    }

    fun dispatch(viewAction: TravelViewAction) {
        viewModelScope.launch {
            userIntent.emit(viewAction)
        }
    }
}