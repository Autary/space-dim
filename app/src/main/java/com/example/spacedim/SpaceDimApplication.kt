package com.example.spacedim

import android.app.Application
import timber.log.Timber

class SpaceDimApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}