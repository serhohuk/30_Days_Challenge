package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.repository.MainRepository
import com.sign.dayschallenge.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChallengeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    fun addChallenge(challenge : Challenge){
        viewModelScope.launch {
            repository.insertChallenge(challenge)
        }
    }

    fun updateChallenge(challenge: Challenge) {
        viewModelScope.launch {
            repository.updateChallenge(challenge)
        }
    }
}