package com.fmt.mvi.learn.net

import com.fmt.mvi.learn.travel.model.Params
import com.fmt.mvi.learn.travel.model.TravelModel
import com.fmt.mvi.learn.travel.model.TravelTabModel
import com.fmt.mvi.learn.url.URLs
import com.fmt.mvi.learn.video.model.VideoListResponseModel
import retrofit2.http.*

interface Api {

    @GET
    suspend fun getTravelTab(@Url url: String = URLs.TRAVEL_TAB_URL): TravelModel

    @POST
    suspend fun getTravelCategoryList(
        @Url url: String,
        @Body params: Params
    ): TravelTabModel

    @GET
    suspend fun getVideoList(
        @Url url: String = URLs.VIDEO_LIST_URL,
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): VideoListResponseModel

}