package com.example.internshipapp.data.remote.dto


import kotlinx.serialization.SerialName


data class CommentsAnswerDtoItem(
    @SerialName("body")
    val body: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("postId")
    val postId: String? = null,
)