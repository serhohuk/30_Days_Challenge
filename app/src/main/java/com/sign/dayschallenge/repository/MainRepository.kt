package com.sign.dayschallenge.repository

import androidx.lifecycle.LiveData
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.data.ChallengeDao
import com.sign.dayschallenge.data.DayState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val dao : ChallengeDao) {

    suspend fun insertChallenge(challenge: Challenge){
        dao.insertChallenge(challenge)
    }

    suspend fun deleteChallenge(challenge: Challenge){
        dao.deleteChallenge(challenge)
    }

    suspend fun updateChallenge(challenge: Challenge){
        dao.updateChallenge(challenge)
    }

    suspend fun updateDayState(id : Int, dayState: List<DayState>){
        dao.updateDayState(id, dayState)
    }

    val readAllData : LiveData<List<Challenge>> = dao.getAllData()

    val getDataId : LiveData<Int> = dao.getAllIds()


}