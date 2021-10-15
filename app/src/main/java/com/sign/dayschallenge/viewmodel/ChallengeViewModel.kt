package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChallengeViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    fun addChallenge(challenge : Challenge){
        viewModelScope.launch {
            repository.insertChallenge(challenge)
        }
    }
}