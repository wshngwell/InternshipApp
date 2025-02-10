package com.example.internshipapp.data

import com.example.internshipapp.data.remote.dto.CommentsAnswerDtoItem
import com.example.internshipapp.data.remote.dto.PostsAnswerItemDto
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.PostEntity


fun PostsAnswerItemDto.toPostEntity() = kotlin.runCatching {
    PostEntity(
        body = body!!,
        id = id!!.toInt(),
        title = title!!,
        userId = userId!!.toInt()
    )
}.getOrElse {
    null
}

fun List<PostsAnswerItemDto>.mapListOfPostsDtoToListOfPosts() = mapNotNull { it.toPostEntity() }

fun CommentsAnswerDtoItem.toCommentEntity() = runCatching {
    CommentEntity(
        body = body!!,
        id = id!!.toInt(),
        email = email!!,
        postId = postId!!.toInt(),
        name = name!!
    )
}.getOrElse {
    null
}

fun List<CommentsAnswerDtoItem>.mapListOfCommentsAnswerItemDtoToListOfCommentsEntity() =
    mapNotNull { it.toCommentEntity() }