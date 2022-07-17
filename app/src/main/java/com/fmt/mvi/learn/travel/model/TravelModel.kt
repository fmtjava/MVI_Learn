package com.fmt.mvi.learn.travel.model

data class TravelModel(
    val params: Params,
    val tabs: List<Tab>,
    val url: String
)

data class Params(
    val contentType: String,
    val districtId: Int,
    var groupChannelCode: String,
    val head: Head,
    val imageCutType: Int,
    val lat: Double,
    val locatedDistrictId: Int,
    val lon: Double,
    val pagePara: PagePara,
    var type: Any
)

data class Tab(
    val groupChannelCode: String,
    val labelName: String,
    val type: Int
)

data class Head(
    val auth: Any,
    val cid: String,
    val ctok: String,
    val cver: String,
    val extension: List<Extension>,
    val lang: String,
    val sid: String,
    val syscode: String
)

data class PagePara(
    var pageIndex: Int,
    var pageSize: Int,
    val sortDirection: Int,
    val sortType: Int
)

data class Extension(
    val name: String,
    val value: String
)