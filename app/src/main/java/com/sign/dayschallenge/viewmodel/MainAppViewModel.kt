package com.sign.dayschallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.data.DayState
import com.sign.dayschallenge.repository.MainRepository
import com.sign.dayschallenge.utils.TimeUtil
import com.sign.dayschallenge.utils.TimeUtil.isTimeOver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainAppViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    val allDataFromDB = repository.readAllData

    fun updateDayState(id : Int, dayState: List<DayState>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDayState(id, dayState)
        }
    }

    fun getNewDayStateList(challenge : Challenge) : List<DayState>{
        val resultDayState = challenge.daysState.toMutableList()

        for ((index, value) in challenge.daysState.withIndex()){
            if (challenge.daysInMillis==null){
                break
            }
            if (isTimeOver(challenge.daysInMillis!![index])){
                break
            }else if (TimeUtil.getCurrentLocalDateNow() == challenge.daysInMillis[index]){
                resultDayState[index] = DayState.TODAY
            } else if (value.name == DayState.EMPTY.name){
                resultDayState[index] = DayState.SKIP_DAY
            }
        }
        return resultDayState
    }

}