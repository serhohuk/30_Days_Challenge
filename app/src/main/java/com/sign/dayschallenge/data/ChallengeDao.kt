package com.sign.dayschallenge.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: Challenge)

    @Query("SELECT * FROM challenge_table")
    fun getAllData() : LiveData<List<Challenge>>

    @Delete
    suspend fun deleteChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)
}