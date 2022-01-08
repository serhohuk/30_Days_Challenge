package com.sign.dayschallenge.utils

interface ActionResponse<T> {

    fun onResponse(type : T)
}