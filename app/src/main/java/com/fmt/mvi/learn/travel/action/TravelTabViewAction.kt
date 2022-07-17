package com.fmt.mvi.learn.travel.action

import com.fmt.mvi.learn.travel.model.Params

sealed class TravelTabViewAction {

    data class Refresh(val url: String, val param: Params) : TravelTabViewAction()

    data class LoadMore(val url: String, val param: Params) : TravelTabViewAction()

}
