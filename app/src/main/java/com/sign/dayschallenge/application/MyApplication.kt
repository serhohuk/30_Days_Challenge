package com.sign.dayschallenge.application

import android.app.Application
import com.sign.dayschallenge.di.AppComponent
import com.sign.dayschallenge.di.DaggerAppComponent

class MyApplication : Application() {

    private lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}