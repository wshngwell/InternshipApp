package com.example.internshipapp.data.remote.dto


import kotlinx.serialization.SerialName

data class PostsAnswerItemDto(
    @SerialName("body")
    val body: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("userId")
    val userId: String? = null,
)