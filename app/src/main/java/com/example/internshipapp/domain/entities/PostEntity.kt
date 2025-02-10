package com.example.internshipapp.domain.entities


data class PostEntity(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
)