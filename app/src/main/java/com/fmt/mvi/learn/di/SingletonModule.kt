package com.fmt.mvi.learn.di

import com.google.gson.Gson
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    fun providerGson(): Gson {
        return Gson()
    }
}