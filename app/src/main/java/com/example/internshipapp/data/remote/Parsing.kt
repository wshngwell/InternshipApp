package com.example.internshipapp.data.remote

import android.util.Log
import com.example.internshipapp.domain.entities.LoadingException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.parseToAuthException(): LoadingException = when (this) {
    is HttpException -> {
        Log.d("TAG", "$message")
        LoadingException.HttpError(code = code(), message = message.toString())
    }

    is IOException -> {
        Log.d("TAG", "$message")
        LoadingException.NetworkError(message = "Не включен интернет")
    }

    is LoadingException.NoPostError -> {
        Log.d("TAG", "$message")
        LoadingException.NoPostError()
    }

    is LoadingException.NoCommentsError -> {
        Log.d("TAG", "$message")
        LoadingException.NoCommentsError()
    }

    else ->{
        Log.d("TAG", "$message")
        LoadingException.OtherError()
    }
}