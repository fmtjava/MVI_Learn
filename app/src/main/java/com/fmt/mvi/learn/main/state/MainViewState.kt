package com.fmt.mvi.learn.main.state

sealed class MainViewState {

    data class InitialDefaultTab(val index: Int) : MainViewState()

}