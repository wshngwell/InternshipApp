package com.example.internshipapp.presentation

import com.example.internshipapp.R
import com.example.internshipapp.domain.entities.LoadingException

fun LoadingException.parseLoadingExceptionToStringResource(): Int {
    return when (this) {
        LoadingException.HttpError -> R.string.httpError
        LoadingException.NetworkError -> R.string.networkError
        LoadingException.NoCommentsError -> R.string.noCommentsError
        LoadingException.NoPostError -> R.string.noPostError
        LoadingException.OtherError -> R.string.otherError
    }
}