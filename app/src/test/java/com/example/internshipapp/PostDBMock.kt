package com.example.internshipapp

import com.example.internshipapp.data.local.PostsDao
import com.example.internshipapp.data.local.dbModels.PostDbModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import org.koin.core.module.dsl.new
import org.koin.dsl.module

class PostDBMock {

    val module = module {

        single<PostsDao> {
            val postDao: PostsDao = object : PostsDao {

                val mockPosts = (0..3).map {
                    PostDbModel(
                        id = it,
                        title = "Title $it",
                        body = "Body $it",
                        userId = it,
                        timeOfInsertion = 13567
                    )
                }

                val postsStateFlow = MutableStateFlow(mockPosts)


                override fun getPostsFromDb(): Flow<List<PostDbModel>> {
                    return postsStateFlow
                }

                override suspend fun addPostToFavourite(postDbModel: PostDbModel) {

                    postsStateFlow.update { it + postDbModel }
                }

                override suspend fun deletePostToFavourite(postId: Int) {
                    val newList = postsStateFlow.value.toMutableList()
                    newList.removeIf { it.id == postId }
                    postsStateFlow.update { newList }
                }
            }
            postDao
        }
    }
}