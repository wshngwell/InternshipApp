package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class GetPostsFromNetworkUseCase(
    private val localRep: ILocalPostRepository,
    private val remoteRep: IPostAndCommentsRemoteRepository
) {
    suspend operator fun invoke(): TResult<List<PostEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext when (val postsFromNetwork = remoteRep.getPostsFromNetwork()) {
                is TResult.Error -> postsFromNetwork
                is TResult.Success -> {
                    val favoriteLisPostsIDS =
                        localRep.postsFromDb.firstOrNull().orEmpty().map { it.id }

                    val newResult = postsFromNetwork.data.map { postFromNetwork ->
                        postFromNetwork.copy(
                            isFavourite = favoriteLisPostsIDS.contains(postFromNetwork.id)
                        )
                    }
                    TResult.Success(newResult)
                }
            }


        }
}