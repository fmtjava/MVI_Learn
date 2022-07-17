package com.fmt.mvi.learn.main.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fmt.mvi.learn.main.action.MainViewAction
import com.fmt.mvi.learn.main.state.MainViewState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val savedHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val SELECTED_TAB_INDEX = "selected_tab_index"
    }

    private val _state = MutableStateFlow<MainViewState>(MainViewState.InitialDefaultTab(0))
    val state: StateFlow<MainViewState>
        get() = _state
    private val userIntent = MutableSharedFlow<MainViewAction>()

    init {
        viewModelScope.launch {
            userIntent.collect { viewAction ->
                when (viewAction) {
                    is MainViewAction.GetCurrentTabIndex -> getDefaultTabSelectedIndex()
                    is MainViewAction.SaveCurrentTabIndex -> saveTabSelectedIndex(viewAction.index)
                }
            }
        }
    }

    fun dispatch(viewAction: MainViewAction) {
        viewModelScope.launch {
            userIntent.emit(viewAction)
        }
    }

    private fun getDefaultTabSelectedIndex() {
        val index = savedHandle.get<Int>(SELECTED_TAB_INDEX) ?: 0
        _state.value = MainViewState.InitialDefaultTab(index)
    }

    private fun saveTabSelectedIndex(index: Int) {
        savedHandle[SELECTED_TAB_INDEX] = index
        _state.value = MainViewState.InitialDefaultTab(index)
    }

}