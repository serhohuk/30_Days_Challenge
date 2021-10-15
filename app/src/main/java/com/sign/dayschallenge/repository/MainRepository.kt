package com.sign.dayschallenge.repository

import androidx.lifecycle.LiveData
import com.sign.dayschallenge.data.Challenge
import com.sign.dayschallenge.data.ChallengeDao
import javax.inject.Inject

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

    val readAllData : LiveData<List<Challenge>> = dao.getAllData()


}