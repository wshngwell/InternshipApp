package com.example.internshipapp.data.remote

import com.example.internshipapp.data.remote.dto.CommentsAnswerDtoItem
import com.example.internshipapp.domain.entities.CommentEntity


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
