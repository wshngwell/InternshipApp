package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCommentsUseCase(
    private val remoteRep: IPostAndCommentsRemoteRepository
) {
    suspend operator fun invoke(postId: Int) = withContext(Dispatchers.IO) {
        remoteRep.getCommentsToPostFromNetwork(postId)
    }

}