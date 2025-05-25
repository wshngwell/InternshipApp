package com.example.internshipapp.data.remote

import com.example.internshipapp.data.toPostEntity
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PostAndCommentsRemoteRepositoryImpl(
    private val apiService: ApiService,
) : IPostAndCommentsRemoteRepository {

    override fun getPostsFromNetwork(): Single<TResult<List<PostEntity>, LoadingException>> {
        return apiService.loadPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { postList ->
                runCatching {
                    val mapPostList = postList.mapNotNull { it.toPostEntity() }

                    if (mapPostList.isEmpty()) {
                        throw LoadingException.NoPostError
                    }
                    TResult.Success<List<PostEntity>, LoadingException>(
                        data = mapPostList
                    )
                }.getOrElse {
                    TResult.Error(
                        exception = it.parseToLoadingException()
                    )
                }
            }
    }


    override suspend fun getCommentsToPostFromNetwork(postId: Int): TResult<List<CommentEntity>,
            LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val commentsList = apiService.loadComments(postId)
                val mapResult = commentsList.mapNotNull { it.toCommentEntity() }
                if (mapResult.isEmpty()) {
                    throw LoadingException.NoCommentsError
                }
                TResult.Success<List<CommentEntity>, LoadingException>(
                    data = mapResult
                )
            }.getOrElse {
                TResult.Error<List<CommentEntity>, LoadingException>(
                    exception = it.parseToLoadingException()
                )
            }

        }
}