package com.example.internshipapp.di

import com.example.internshipapp.data.local.PostLocalRepository
import com.example.internshipapp.data.local.PostsDao
import com.example.internshipapp.data.local.PostsDatabase
import com.example.internshipapp.data.remote.ApiFactory
import com.example.internshipapp.data.remote.ApiService
import com.example.internshipapp.data.remote.AuthRepositoryImpl
import com.example.internshipapp.data.remote.PostAndCommentsRemoteRepositoryImpl
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.repositories.IAuthRepository
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import com.example.internshipapp.domain.usecases.AuthUseCase
import com.example.internshipapp.domain.usecases.GetCommentsUseCase
import com.example.internshipapp.domain.usecases.GetPostsFromNetworkUseCase
import com.example.internshipapp.domain.usecases.AddPostToFavouriteUseCase
import com.example.internshipapp.domain.usecases.DeletePostsFromFavouriteUseCase
import com.example.internshipapp.domain.usecases.GetFavouritePostsUseCase
import com.example.internshipapp.presentation.feature1.LoginViewModel
import com.example.internshipapp.presentation.feature2.PostWithCommentsViewModel
import com.example.internshipapp.presentation.feature2.PostsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    //useCases
    factory<AuthUseCase> {
        AuthUseCase(repository = get<IAuthRepository>())
    }
    factory<GetCommentsUseCase> {
        GetCommentsUseCase(
            remoteRep = get<IPostAndCommentsRemoteRepository>()
        )
    }
    factory<GetPostsFromNetworkUseCase> {
        GetPostsFromNetworkUseCase(
            localRep = get<ILocalPostRepository>(),
            remoteRep = get<IPostAndCommentsRemoteRepository>()
        )
    }
    factory<AddPostToFavouriteUseCase> {
        AddPostToFavouriteUseCase(
            localRep = get<ILocalPostRepository>()
        )
    }
    factory<DeletePostsFromFavouriteUseCase> {
        DeletePostsFromFavouriteUseCase(
            iLocalPostRepository = get<ILocalPostRepository>()
        )
    }
    factory<GetFavouritePostsUseCase> {
        GetFavouritePostsUseCase(
            iLocalPostRepository = get<ILocalPostRepository>()
        )
    }
    //repositories
    single<IAuthRepository> {
        AuthRepositoryImpl()
    }
    single<ILocalPostRepository> {
        PostLocalRepository(postsDao = get<PostsDao>())
    }
    single<IPostAndCommentsRemoteRepository> {
        PostAndCommentsRemoteRepositoryImpl(apiService = get<ApiService>())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(authUseCase = get<AuthUseCase>())
    }
    viewModel<PostsViewModel> {
        PostsViewModel(
            getPostsFromNetworkUseCase = get<GetPostsFromNetworkUseCase>(),
            addPostToFavouriteUseCase = get<AddPostToFavouriteUseCase>(),
            deletePostsFromFavouriteUseCase = get<DeletePostsFromFavouriteUseCase>(),
            getFavouritePostsUseCase = get<GetFavouritePostsUseCase>()
        )
    }
    viewModel<PostWithCommentsViewModel> { (postEntity: PostEntity) ->
        PostWithCommentsViewModel(
            post = postEntity,
            getCommentsUseCase = get<GetCommentsUseCase>(),
            addPostToFavouriteUseCase = get<AddPostToFavouriteUseCase>(),
            deletePostUseCase = get<DeletePostsFromFavouriteUseCase>(),
            getFavouritePostsUseCase = get<GetFavouritePostsUseCase>()

        )
    }
    single<PostsDao> {
        PostsDatabase.getInstance(application = androidApplication()).getPostsDao()
    }
    single<ApiService> {
        ApiFactory.apiService(application = androidApplication())
    }
}

