package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.repositories.ILocalPostRepository

class GetFavouritePostsUseCase(
    private val iLocalPostRepository: ILocalPostRepository
) {
    operator fun invoke() = iLocalPostRepository.postsFromDb
}