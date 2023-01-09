package com.fmt.mvi.learn.travel.model

data class TravelTabModel(
    val totalCount: Int,
    val resultList: List<TravelItem>
)

data class TravelItem(
    val type: Int,
    val article: Article
)

data class Article(
    val articleId: Int,
    val productType: Int,
    val sourceType: Int,
    val articleTitle: String,
    val author: Author,
    val images: List<Images>,
    val hasVideo: Boolean,
    val readCount: Int,
    val likeCount: Int,
    val commentCount: Int,
    val urls: List<Urls>,
    val tags: List<Tags>,
    val topics: List<Topics>,
    val pois: List<Pois>,
    val publishTime: String,
    val publishTimeDisplay: String,
    val shootTime: String,
    val shootTimeDisplay: String,
    val level: Int,
    val distanceText: String,
    val isLike: Boolean,
    val imageCounts: Int,
    val isCollected: Boolean,
    val collectCount: Int,
    val articleStatus: Int,
    val poiName: String
)

data class Author(
    val authorId: Int,
    val nickName: String,
    val clientAuth: String,
    val jumpUrl: String,
    val coverImage: CoverImage
)

data class CoverImage(
    val dynamicUrl: String,
    val originalUrl: String
)

data class Images(
    val imageId: Int,
    val dynamicUrl: String,
    val originalUrl: String,
    val width: Double,
    val height: Double,
    val mediaType: Int,
    val lat: Double,
    val lon: Double
)

data class Urls(
    val version: String,
    val appUrl: String,
    val h5Url: String?,
    val wxUrl: String
)

data class Tags(
    val tagId: Int,
    val tagName: String,
    val tagLevel: Int,
    val parentTagId: Int,
    val source: Int,
    val sortIndex: Int
)

data class Topics(
    val topicId: Int,
    val topicName: String,
    val level: Int
)

data class Pois(
    val poiType: Int,
    val poiId: Int,
    val poiName: String,
    val districtId: Int,
    val districtName: String,
    val poiExt: PoiExt,
    val source: Int,
    val isMain: Int,
    val isInChin: Boolean
)

data class PoiExt(
    val h5Url: String,
    val appUrl: String
)
