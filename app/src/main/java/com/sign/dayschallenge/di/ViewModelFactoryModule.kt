package com.sign.dayschallenge.di

import androidx.lifecycle.ViewModelProvider
import com.sign.dayschallenge.viewmodel.ChallengeViewModel
import com.sign.dayschallenge.viewmodel.ChallengeViewModelFactory
import com.sign.dayschallenge.viewmodel.MainFragmentViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Named

@Module
abstract class ViewModelFactoryModule {

    @Binds
    @Named("main_view_model")
    abstract fun bindsMainFragmentViewModelFactory(factory : MainFragmentViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @Named("challenge_view_model")
    abstract fun bindsChallengeViewModelFactory(factory : ChallengeViewModelFactory) : ViewModelProvider.Factory
}