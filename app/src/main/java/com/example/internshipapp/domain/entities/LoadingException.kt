package com.example.internshipapp.domain.entities

sealed class LoadingException(val msg: String) : Throwable() {
    data class NetworkError(override val message: String = "Нет интернета") :
        LoadingException(message)

    data class HttpError(val code: Int, override val message: String) : LoadingException(message)
    data class NoPostError(override val message: String = "Список постов пустой") :
        LoadingException(message)

    data class NoCommentsError(override val message: String = "Список комментариев пустой") :
        LoadingException(message)

    data class OtherError(override val message: String = "Иная ошибка") : LoadingException(message)
}