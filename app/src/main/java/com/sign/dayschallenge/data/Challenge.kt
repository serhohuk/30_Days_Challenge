package com.sign.dayschallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "challenge_table")
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var title : String,
    var imgResource : Int,
    var description : String?,
    var daysPassed : Int,
    var daysInMillis : List<Long>?,
    var daysState : List<DayState>,
    var challengeEnded : Boolean = false
) : Serializable
