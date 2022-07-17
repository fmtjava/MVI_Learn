package com.fmt.mvi.learn.main.action

sealed class MainViewAction {

    object GetCurrentTabIndex : MainViewAction()

    data class SaveCurrentTabIndex(val index: Int) : MainViewAction()
}
