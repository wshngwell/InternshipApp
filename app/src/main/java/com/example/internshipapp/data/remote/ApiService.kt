package com.example.internshipapp.data.remote

import com.andretietz.retrofit.ResponseCache
import com.example.internshipapp.data.remote.dto.CommentsAnswerDto
import com.example.internshipapp.data.remote.dto.PostsAnswerDto
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("posts")
    @ResponseCache(5, TimeUnit.MINUTES)
    fun loadPosts(): Single<PostsAnswerDto>

    @GET("comments")
    suspend fun loadComments(@Query("postId") postId: Int): CommentsAnswerDto

    @Streaming
    @GET
    suspend fun downLoadFile(
        @Url urlOfFile: String
    ): ResponseBody

}