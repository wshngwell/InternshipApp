package com.example.internshipapp

import android.util.Log

fun myLog(msg: String) {
    if (BuildConfig.DEBUG) {
        runCatching {
            Log.d("Error", msg)
        }.getOrElse {
            println(msg)
        }
    }
}