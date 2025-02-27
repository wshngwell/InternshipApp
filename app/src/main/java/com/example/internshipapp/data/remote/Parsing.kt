package com.example.internshipapp.data.remote

import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.myLog
import retrofit2.HttpException
import java.io.IOException

fun Throwable.parseToAuthException(): LoadingException {
    myLog(this.stackTraceToString())
    return when (this) {
        is HttpException -> LoadingException.HttpError

        is IOException -> LoadingException.NetworkError

        is LoadingException -> this

        else -> LoadingException.OtherError
    }
}