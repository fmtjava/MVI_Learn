package com.fmt.mvi.learn.travel.state

import com.fmt.mvi.learn.travel.model.TravelItem

sealed class TravelTabViewState {

    data class RefreshSuccess(val travelList: List<TravelItem>) : TravelTabViewState()

    data class LoadMoreSuccess(val travelList: List<TravelItem>) : TravelTabViewState()

    data class LoadError(val errorMsg: String) : TravelTabViewState()
}
