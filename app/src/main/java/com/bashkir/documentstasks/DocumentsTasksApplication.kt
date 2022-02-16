package com.bashkir.documentstasks

import android.app.Application
import com.airbnb.mvrx.Mavericks
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DocumentsTasksApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@DocumentsTasksApplication)
            modules(modules)
        }
    }
}