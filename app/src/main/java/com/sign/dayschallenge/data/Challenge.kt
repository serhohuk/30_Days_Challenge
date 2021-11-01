package com.sign.dayschallenge.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "challenge_table")
data class Challenge(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val imgResource : Int,
    val description : String?,
    val daysPassed : Int,
    val daysState : List<DayState>
) : Serializable
