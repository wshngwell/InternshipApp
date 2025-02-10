package com.example.internshipapp

import android.util.Log

fun myLog(msg: String) {
    if (BuildConfig.DEBUG) {
        Log.e("Error", msg)
    } else {
        println(msg)
    }
}