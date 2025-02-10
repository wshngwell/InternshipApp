package com.example.internshipapp.data.remote

import android.app.Application
import com.andretietz.retrofit.responseCache
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {

    private fun initialize(application: Application) =
        Cache(application.cacheDir, (5 * 1024 * 1024).toLong())


    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun apiService(application: Application) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(MyOkHttpClient(isSafe = false).get())
        .build()
        .responseCache(initialize(application))
        .create(ApiService::class.java)


}