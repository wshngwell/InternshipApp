package com.example.internshipapp.data.remote

import android.app.Application
import com.andretietz.retrofit.responseCache
import com.example.internshipapp.data.remote.OkhttpCache.setOkhttpCache
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun apiService(application: Application) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(MyOkHttpClient(isSafe = false).get())
        .build()
        .setOkhttpCache(application)
        .create(ApiService::class.java)


}