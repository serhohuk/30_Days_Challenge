package com.sign.dayschallenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sign.dayschallenge.data.AppDatabase
import com.sign.dayschallenge.data.ChallengeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(private val application: Application) {

    @Singleton
    @Provides
    fun provideDatabase(context: Context)=
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_db")
            .build()

    @Singleton
    @Provides
    fun provideApplication() : Application = application

    @Singleton
    @Provides
    fun provideContext() : Context = application

    @Singleton
    @Provides
    fun provideDao(db : AppDatabase) : ChallengeDao = db.getChallengeDao()

}