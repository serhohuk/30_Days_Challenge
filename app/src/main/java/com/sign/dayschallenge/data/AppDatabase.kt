package com.sign.dayschallenge.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sign.dayschallenge.utils.Converter

@Database(entities = [Challenge::class],version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getChallengeDao() : ChallengeDao
}