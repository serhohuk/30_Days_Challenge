package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sign.dayschallenge.repository.MainRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChallengeViewModelFactory @Inject constructor(private val repository: MainRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChallengeViewModel(repository) as T
    }
}