package com.fmt.mvi.learn.net

import com.fmt.mvi.learn.BuildConfig
import com.fmt.mvi.learn.gobal.ConfigKeys
import com.fmt.mvi.learn.gobal.Configurator
import com.orhanobut.logger.Logger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * 若网络不你用Hilt进行依赖注入，则可以使用该文件下的工具类
 */
private val retrofit by lazy {
    Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Configurator.getConfiguration<String>(ConfigKeys.API_HOST))
        .build()
}

private val okHttpClient by lazy {
    OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor {
                    Logger.d(it)

                }.setLevel(HttpLoggingInterceptor.Level.BODY))
            }
        }
        .build()
}

object ApiService : Api by retrofit.create(Api::class.java)