package com.yacine.diceroller

import android.app.Application
import timber.log.Timber



class TreeLogger: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}