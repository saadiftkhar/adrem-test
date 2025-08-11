package com.adremtest.android

import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdremAppClass : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

    }
}
