package com.example.internshipapp.data.remote

import android.app.Application
import com.example.internshipapp.data.remote.OkhttpCache.setOkhttpCache
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiFactory {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    fun apiService(application: Application) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(MyOkHttpClient(isSafe = false).get())
        .build()
        .setOkhttpCache(application)
        .create(ApiService::class.java)


}