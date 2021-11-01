package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.repository.MainRepository
import com.sign.dayschallenge.utils.Resource
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val allDataFromDB = repository.readAllData

}