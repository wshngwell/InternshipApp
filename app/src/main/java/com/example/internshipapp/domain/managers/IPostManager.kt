package com.example.internshipapp.domain.managers

import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult

interface IPostManager {
    suspend fun getPostsFromNetwork(): TResult<List<PostEntity>, LoadingException>
    suspend fun getCommentsToPostFromNetwork(postId: Int): TResult<List<CommentEntity>, LoadingException>
}