package com.ditta.worldbeers

import android.app.Application
import com.ditta.worldbeers.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WorldBeerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@WorldBeerApplication)
            modules(appModule)
        }
    }
}