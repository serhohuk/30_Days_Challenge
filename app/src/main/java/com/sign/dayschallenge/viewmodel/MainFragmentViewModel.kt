package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.repository.MainRepository
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val allDataFromDB : LiveData<List<Challenge>> = repository.readAllData


    fun getAllDataDB() : LiveData<List<Challenge>>{
        return allDataFromDB
    }
}