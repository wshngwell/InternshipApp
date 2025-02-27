package com.example.internshipapp.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PostEntity(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    val isFavourite: Boolean
) : Parcelable