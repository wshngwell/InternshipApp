package com.example.internshipapp.domain.managers

import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.repositories.IPostAndCommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PostManagerImpl(
    private val repository: IPostAndCommentsRepository
) : IPostManager {
    override suspend fun getPostsFromNetwork(): TResult<List<PostEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            repository.getPostsFromNetwork()
        }

    override suspend fun getCommentsToPostFromNetwork(postId: Int): TResult<List<CommentEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            repository.getCommentsToPostFromNetwork(postId)
        }
}