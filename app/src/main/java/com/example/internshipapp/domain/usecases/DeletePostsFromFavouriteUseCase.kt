package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.repositories.ILocalPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeletePostsFromFavouriteUseCase(
    private val iLocalPostRepository: ILocalPostRepository
) {
    suspend operator fun invoke(postId: Int) = withContext(Dispatchers.IO) {
        iLocalPostRepository.deletePostToFavourite(postId)
    }

}