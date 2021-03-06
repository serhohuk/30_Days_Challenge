package com.sign.dayschallenge.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sign.dayschallenge.utils.Converter

@Database(entities = [Challenge::class],version = 3)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getChallengeDao() : ChallengeDao
}

val MIGRATION_1_2 = object : Migration(1,2){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE challenge_table ADD COLUMN daysInMillis TEXT")
    }

}

val MIGRATION_2_3 = object : Migration(2,3){
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE challenge_table ADD COLUMN challengeEnded INTEGER NOT NULL DEFAULT 0")
    }

}

