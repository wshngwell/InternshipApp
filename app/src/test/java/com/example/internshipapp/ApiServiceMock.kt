package com.example.internshipapp

import com.example.internshipapp.data.remote.ApiService
import com.example.internshipapp.data.remote.dto.CommentsAnswerDto
import com.example.internshipapp.data.remote.dto.PostsAnswerDto
import com.example.internshipapp.data.remote.dto.PostsAnswerItemDto
import okhttp3.ResponseBody
import org.koin.dsl.module

class ApiServiceMock {
    val module = module {
        single<ApiService> {
            val apiService: ApiService = object : ApiService {

                override suspend fun loadPosts(): PostsAnswerDto {
                    val mockPosts = List(3) {
                        PostsAnswerItemDto(
                            id = "$it",
                            title = "Title $it",
                            body = "Body $it",
                            userId = "$it",
                        )
                    }

                    val postAnswerDto = PostsAnswerDto()
                    postAnswerDto.addAll(mockPosts)
                    return postAnswerDto
                }

                override suspend fun loadComments(postId: Int): CommentsAnswerDto {
                    TODO("Not yet implemented")
                }

                override suspend fun downLoadFile(urlOfFile: String): ResponseBody {
                    TODO("Not yet implemented")
                }
            }
            apiService
        }

    }
}