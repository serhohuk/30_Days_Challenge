package com.sign.dayschallenge.utils


import com.sign.dayschallenge.data.DayState
import java.text.SimpleDateFormat
import java.util.*



object TimeUtil {

    const val DAY_IN_MILLIS = 86400000L

    fun getCurrentLocalDateNow(): Long {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val date = Date()
        val formatedDate = formatter.format(date)
        val newDate: Date = formatter.parse(formatedDate)!!
        return newDate.time
    }

    fun generateListOfTimes() : List<Long>{
        var currentTime = getCurrentLocalDateNow()
        val result = mutableListOf<Long>()
        for (i in 1..30){
            result.add(currentTime)
            currentTime+= DAY_IN_MILLIS
        }
        return result
    }


}