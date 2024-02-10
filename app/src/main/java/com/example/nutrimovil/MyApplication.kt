package com.example.nutrimovil

import android.app.Application
import com.example.nutrimovil.data.models.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sessionModule = module {
            single { SessionManager }
        }
        startKoin {
            androidContext(this@MyApplication)
            modules(sessionModule)
        }
    }
}