package com.fmt.mvi.learn.gobal

import com.google.gson.Gson

/**
 * 若不你用Hilt进行依赖注入，则可以使用该文件下的工具类
 */
val gson by lazy { Gson() }

inline fun <reified T> fromJson(json: String): T {
    return gson.fromJson(json, T::class.java)
}