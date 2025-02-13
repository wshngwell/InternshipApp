package com.example.internshipapp.domain.entities

sealed class LoadingException : Throwable() {

    data object NetworkError : LoadingException()

    data object HttpError : LoadingException()

    data object NoPostError : LoadingException()

    data object NoCommentsError : LoadingException()

    data object OtherError : LoadingException()
}