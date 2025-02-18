package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.PostEntity
import kotlinx.coroutines.flow.SharedFlow

interface ILocalPostRepository {
    val postsFromDb: SharedFlow<List<PostEntity>>


    suspend fun addPostToFavourite(post: PostEntity)

    suspend fun deletePostToFavourite(postId: Int)
}