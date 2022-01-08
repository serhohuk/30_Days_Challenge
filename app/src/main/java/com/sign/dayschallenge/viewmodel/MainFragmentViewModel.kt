package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.repository.MainRepository
import com.sign.dayschallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val allDataFromDB = repository.readAllData

    fun deleteItem(challenge : Challenge) : List<Challenge>{
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteChallenge(challenge)
        }
        val list = allDataFromDB.value?.toMutableList()
        list?.remove(challenge)
        return list!!
    }

}