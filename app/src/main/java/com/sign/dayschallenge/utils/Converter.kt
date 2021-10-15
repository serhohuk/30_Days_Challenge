package com.sign.dayschallenge.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sign.dayschallenge.data.DayState

class Converter {

    @TypeConverter
    fun fromList(list : List<DayState>) : String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(string : String) : List<DayState>{
        val type = object : TypeToken<List<DayState>>(){}.type
        return Gson().fromJson(string, type)
    }
}