package com.example.internshipapp

import android.util.Log

fun myLog(msg: String) {
    if (BuildConfig.DEBUG) {
        runCatching {
            Log.d("!!!", msg)
        }.getOrElse {
            println(msg)
        }
    }
}