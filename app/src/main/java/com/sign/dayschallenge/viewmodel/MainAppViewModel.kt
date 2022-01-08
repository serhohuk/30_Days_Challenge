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

    fun updateChallenge(challenge: Challenge){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateChallenge(challenge)
        }
    }

    fun getNewDayStateList(challenge : Challenge) : List<DayState>{
        val resultDayState = challenge.daysState.toMutableList()
        for ((index, value) in challenge.daysState.withIndex()){
            if (isTimeOver(challenge.daysInMillis!![index])){
                if(value.name == DayState.TODAY.name){
                    resultDayState[index] = DayState.SKIP_DAY
                } else break
            }else if (TimeUtil.getCurrentLocalDateNow() == challenge.daysInMillis!![index] && resultDayState[index].name != DayState.COMPLETE_DAY.name ){
                resultDayState[index] = DayState.TODAY
            } else if (isTimeOver(challenge.daysInMillis!![index]) && value.name == DayState.EMPTY.name && resultDayState[index].name != DayState.COMPLETE_DAY.name){
                resultDayState[index] = DayState.SKIP_DAY
            }
        }
        return resultDayState
    }

    fun updateOneItem(challenge: Challenge, itemStateOrdinal : Int, position : Int): Challenge {
        val resultList = challenge.daysState.toMutableList()
        val resultChallenge = challenge
        when(itemStateOrdinal){
           1->{
               resultList[position] = DayState.COMPLETE_DAY
               resultChallenge.daysPassed = challenge.daysPassed+1
           }
           3->{
               resultList[position] = DayState.SKIP_DAY
               resultChallenge.daysPassed = challenge.daysPassed-1
           }
        }
        resultChallenge.daysState = resultList
        return resultChallenge
    }

}