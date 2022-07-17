package com.fmt.mvi.learn.gobal

import com.google.gson.Gson

val gson = Gson()

inline fun <reified T> fromJson(json: String): T {
    return gson.fromJson(json, T::class.java)
}