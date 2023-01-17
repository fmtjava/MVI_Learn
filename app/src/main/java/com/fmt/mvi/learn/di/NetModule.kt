package com.fmt.mvi.learn.di

import com.fmt.mvi.learn.BuildConfig
import com.fmt.mvi.learn.gobal.ConfigKeys
import com.fmt.mvi.learn.gobal.Configurator
import com.fmt.mvi.learn.net.Api
import com.orhanobut.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    fun providerOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor {
                        Logger.d(it)

                    }.setLevel(HttpLoggingInterceptor.Level.BODY))
                }
            }
            .build()
    }

    @Provides
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Configurator.getConfiguration<String>(ConfigKeys.API_HOST))
            .build()
    }

    @Provides
    fun providerApiService(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }
}