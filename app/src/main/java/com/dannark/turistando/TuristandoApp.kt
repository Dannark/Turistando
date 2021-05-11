package com.dannark.turistando

import android.app.Application
import timber.log.Timber

class TuristandoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}