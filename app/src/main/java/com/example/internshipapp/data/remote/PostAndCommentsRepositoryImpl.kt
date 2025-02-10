package com.example.internshipapp.data.remote

import com.example.internshipapp.data.mapListOfPostsDtoToListOfPosts
import com.example.internshipapp.data.toCommentEntity
import com.example.internshipapp.data.toPostEntity
import com.example.internshipapp.domain.entities.CommentEntity
import com.example.internshipapp.domain.entities.LoadingException
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.entities.TResult
import com.example.internshipapp.domain.repositories.IPostAndCommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PostAndCommentsRepositoryImpl(
    private val apiService: ApiService,
) : IPostAndCommentsRepository {

    override suspend fun getPostsFromNetwork(): TResult<List<PostEntity>, LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val postsList = apiService.loadPosts()
                val mapPostList = postsList.mapNotNull { it.toPostEntity() }
                val failedPosts = mapPostList.size - postsList.size
                if (failedPosts != 0) {
                    throw LoadingException.NoPostError()
                }
                TResult.Success<List<PostEntity>, LoadingException>(
                    data = apiService.loadPosts().mapListOfPostsDtoToListOfPosts()
                )
            }.getOrElse {
                TResult.Error<List<PostEntity>, LoadingException>(
                    exception = it.parseToAuthException()
                )
            }


        }

    override suspend fun getCommentsToPostFromNetwork(postId: Int): TResult<List<CommentEntity>,
            LoadingException> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val commentsList = apiService.loadComments(postId)
                val mapResult = commentsList.mapNotNull { it.toCommentEntity() }
                val failedComments = mapResult.size - commentsList.size
                if (failedComments != 0) {
                    throw LoadingException.NoCommentsError()
                }
                TResult.Success<List<CommentEntity>, LoadingException>(
                    data = mapResult
                )
            }.getOrElse {
                TResult.Error<List<CommentEntity>, LoadingException>(
                    exception = it.parseToAuthException()
                )
            }

        }
}