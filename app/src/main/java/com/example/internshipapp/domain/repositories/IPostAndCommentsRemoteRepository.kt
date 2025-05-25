package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import io.reactivex.rxjava3.core.Single

interface IPostAndCommentsRemoteRepository {
    fun getPostsFromNetwork(): Single<TResult<List<PostEntity>, LoadingException>>
    suspend fun getCommentsToPostFromNetwork(postId: Int): TResult<List<CommentEntity>, LoadingException>
}