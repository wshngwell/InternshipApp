package com.example.internshipapp.data.remote

import com.andretietz.retrofit.ResponseCache
import com.example.internshipapp.data.remote.dto.CommentsAnswerDto
import com.example.internshipapp.data.remote.dto.PostsAnswerDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("posts")
    @ResponseCache(5, TimeUnit.MINUTES)
    suspend fun loadPosts(): PostsAnswerDto

    @GET("comments")
    suspend fun loadComments(@Query("postId") postId: Int): CommentsAnswerDto
}