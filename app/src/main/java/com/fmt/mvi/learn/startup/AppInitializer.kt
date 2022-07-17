package com.fmt.mvi.learn.startup

import android.content.Context
import androidx.startup.Initializer
import com.fmt.mvi.learn.gobal.Configurator
import com.fmt.mvi.learn.url.URLs
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Configurator.withApiHost(URLs.VIDEO_LIST_URL)
            .withApplicationContext(context.applicationContext)
            .configure()
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}