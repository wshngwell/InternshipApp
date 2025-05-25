package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import io.reactivex.rxjava3.core.Single

class GetPostsFromNetworkUseCase(
    private val localRep: ILocalPostRepository,
    private val remoteRep: IPostAndCommentsRemoteRepository
) {
    operator fun invoke(): Single<TResult<List<PostEntity>, LoadingException>> =
        remoteRep.getPostsFromNetwork()
    /*when (it) {
        is TResult.Error -> it
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
    }*/
}