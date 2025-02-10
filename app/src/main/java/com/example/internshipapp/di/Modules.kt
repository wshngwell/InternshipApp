package com.example.internshipapp.di

import com.example.internshipapp.data.remote.ApiFactory
import com.example.internshipapp.data.remote.ApiService
import com.example.internshipapp.data.remote.PostAndCommentsRepositoryImpl
import com.example.internshipapp.data.remote.AuthRepositoryImpl
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.managers.IManager
import com.example.internshipapp.domain.managers.IPostManager
import com.example.internshipapp.domain.managers.AuthManagerImpl
import com.example.internshipapp.domain.managers.PostManagerImpl
import com.example.internshipapp.domain.repositories.IPostAndCommentsRepository
import com.example.internshipapp.domain.repositories.IAuthRepository
import com.example.internshipapp.presentation.feature1.LoginViewModel
import com.example.internshipapp.presentation.feature2.PostWithCommentsViewModel
import com.example.internshipapp.presentation.feature2.PostsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module


val appModule = module {

    single<IAuthRepository> {
        AuthRepositoryImpl()
    }
    single<IManager> {
        AuthManagerImpl(repository = get<IAuthRepository>())
    }
    single<IPostManager> {
        PostManagerImpl(repository = get<IPostAndCommentsRepository>())
    }
    single<IPostAndCommentsRepository> {
        PostAndCommentsRepositoryImpl(apiService = get<ApiService>())
    }
    viewModel<LoginViewModel> {
        LoginViewModel(manager = get<IManager>())
    }
    viewModel<PostsViewModel> {
        PostsViewModel(manager = get<IPostManager>())
    }
    viewModel<PostWithCommentsViewModel> { (postEntity: PostEntity) ->
        PostWithCommentsViewModel(postEntity = postEntity, manager = get<IPostManager>())
    }
    single<ApiService> {
        ApiFactory.apiService(application = androidApplication())
    }
}

