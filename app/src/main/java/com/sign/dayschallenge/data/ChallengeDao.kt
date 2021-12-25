package com.sign.dayschallenge.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChallengeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallenge(challenge: Challenge)

    @Query("SELECT * FROM challenge_table")
    fun getAllData() : LiveData<List<Challenge>>

    @Query("SELECT id FROM challenge_table")
    fun getAllIds() : LiveData<Int>

    @Query("UPDATE challenge_table SET daysState =:days WHERE id =:id")
    suspend fun updateDayState(id: Int, days: List<DayState>)

    @Delete
    suspend fun deleteChallenge(challenge: Challenge)

    @Update
    suspend fun updateChallenge(challenge: Challenge)
}