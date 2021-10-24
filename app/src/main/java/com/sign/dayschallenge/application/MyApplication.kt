package com.sign.dayschallenge.application

import android.app.Application
import com.sign.dayschallenge.di.AppComponent
import com.sign.dayschallenge.di.DaggerAppComponent
import com.sign.dayschallenge.di.RoomModule
import com.sign.dayschallenge.ui.fragments.CreateChallengeFragment
import com.sign.dayschallenge.ui.fragments.MainFragment

class MyApplication : Application() {

    lateinit var appComponent : AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().roomModule(RoomModule(this)).build()
    }
}