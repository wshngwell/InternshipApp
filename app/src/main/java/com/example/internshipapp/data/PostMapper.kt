package com.example.internshipapp.data

import com.example.internshipapp.data.local.dbModels.PostDbModel
import com.example.internshipapp.data.remote.dto.PostsAnswerItemDto
import com.example.internshipapp.domain.entities.PostEntity

fun PostDbModel.toPostEntity() =
    PostEntity(
        body = body,
        id = id,
        title = title,
        userId = userId,
        isFavourite = true
    )


fun PostEntity.toPostDbModel() =
    PostDbModel(
        body = body,
        id = id,
        title = title,
        userId = userId,
        timeOfInsertion = System.currentTimeMillis()
    )

fun PostsAnswerItemDto.toPostEntity() = kotlin.runCatching {
    PostEntity(
        body = body!!,
        id = id!!.toInt(),
        title = title!!,
        userId = userId!!.toInt(),
        isFavourite = false
    )
}.getOrElse {
    null
}

//fun List<PostEntity>.mapListOfPostsDtoToListOfPosts() = mapNotNull { it.toPostEntity() }