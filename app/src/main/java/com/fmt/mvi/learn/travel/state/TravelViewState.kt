package com.fmt.mvi.learn.travel.state

import com.fmt.mvi.learn.travel.model.TravelModel

sealed class TravelViewState {

    object LoadingState : TravelViewState()

    data class LoadSuccess(val travelTabModel: TravelModel) : TravelViewState()

    data class LoadFail(val errorMsg: String) : TravelViewState()
}
