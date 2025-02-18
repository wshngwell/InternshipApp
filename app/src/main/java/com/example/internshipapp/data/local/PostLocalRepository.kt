package com.example.internshipapp.data.local

import com.example.internshipapp.data.toPostDbModel
import com.example.internshipapp.data.toPostEntity
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class PostLocalRepository(
    private val postsDao: PostsDao
) : ILocalPostRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val postsFromDb: SharedFlow<List<PostEntity>> =
        postsDao.getPostsFromDb().map { listOfPosts ->
            listOfPosts
                .sortedByDescending { it.timeOfInsertion }
                .map { post ->
                    post.toPostEntity()
                }
        }
            .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
            .distinctUntilChanged()
            .shareIn(
                scope = scope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 10_000),
                replay = 1
            )

    override suspend fun addPostToFavourite(post: PostEntity) = withContext(Dispatchers.IO) {
        return@withContext postsDao.addPostToFavourite(post.toPostDbModel())
    }

    override suspend fun deletePostToFavourite(postId: Int) = withContext(Dispatchers.IO) {
        return@withContext postsDao.deletePostToFavourite(postId)
    }
}