package com.fmt.mvi.learn.travel.action

sealed class TravelViewAction {

    object GetTravelTabs : TravelViewAction()

    object Retry : TravelViewAction()

}
