package com.mvvm.demo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Created by kailash
 */

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }

}