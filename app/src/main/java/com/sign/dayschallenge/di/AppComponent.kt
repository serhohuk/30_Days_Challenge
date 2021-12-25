package com.sign.dayschallenge.di

import com.sign.dayschallenge.ui.MainActivity
import com.sign.dayschallenge.ui.fragments.CreateChallengeFragment
import com.sign.dayschallenge.ui.fragments.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RoomModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(challengeFragment: CreateChallengeFragment)

    fun inject(mainActivity: MainActivity)
}