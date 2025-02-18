package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddPostToFavouriteUseCase(
    private val localRep: ILocalPostRepository
) {

    suspend fun addPostToFavourite(post: PostEntity) = withContext(Dispatchers.IO) {
        localRep.addPostToFavourite(post)
    }
}