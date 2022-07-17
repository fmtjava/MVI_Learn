package com.fmt.mvi.learn.gobal

import android.content.Context

/**
 * 定义全局变量配置
 */
object Configurator {

    private val CONFIGS = mutableMapOf<Any, Any>()

    //链式初始化相关变量
    fun withApiHost(host: String): Configurator {
        CONFIGS[ConfigKeys.API_HOST] = host
        return this
    }

    fun withApplicationContext(context: Context): Configurator {
        CONFIGS[ConfigKeys.APPLICATION_CONTEXT] = context
        return this
    }

    fun configure() {
        CONFIGS[ConfigKeys.CONFIG_READY] = true
    }

    private fun checkConfiguration() {
        val isReady = CONFIGS[ConfigKeys.CONFIG_READY] as Boolean
        if (!isReady) {
            throw  RuntimeException("Configuration is not ready,call configure");
        }
    }

    fun <T> getConfiguration(key: Any): T {
        checkConfiguration()
        CONFIGS[key] ?: throw NullPointerException("$key IS NULL")
        return CONFIGS[key] as T
    }
}