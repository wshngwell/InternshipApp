package com.example.internshipapp.di

import androidx.media3.exoplayer.ExoPlayer
import com.example.internshipapp.data.feature13.MusicPlayerRepositoryImpl
import com.example.internshipapp.data.feature14.DownLoadFileRepositoryImpl
import com.example.internshipapp.data.local.ConsumersRepositoryImpl
import com.example.internshipapp.data.local.PostLocalRepository
import com.example.internshipapp.data.local.PostsDao
import com.example.internshipapp.data.local.PostsDatabase
import com.example.internshipapp.data.local.dbModels.ConsumersDao
import com.example.internshipapp.data.remote.ApiFactory
import com.example.internshipapp.data.remote.ApiService
import com.example.internshipapp.data.remote.AuthRepositoryImpl
import com.example.internshipapp.data.remote.PostAndCommentsRemoteRepositoryImpl
import com.example.internshipapp.data.remote.feature6.PaginationRepository
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.domain.repositories.IAuthRepository
import com.example.internshipapp.domain.repositories.IConsumersRepository
import com.example.internshipapp.domain.repositories.IDownLoadFileRepository
import com.example.internshipapp.domain.repositories.ILocalPostRepository
import com.example.internshipapp.domain.repositories.IMusicPlayerRepository
import com.example.internshipapp.domain.repositories.IPaginationRepository
import com.example.internshipapp.domain.repositories.IPostAndCommentsRemoteRepository
import com.example.internshipapp.domain.usecases.AddPostToFavouriteUseCase
import com.example.internshipapp.domain.usecases.AuthUseCase
import com.example.internshipapp.domain.usecases.DeletePostsFromFavouriteUseCase
import com.example.internshipapp.domain.usecases.GetCommentsUseCase
import com.example.internshipapp.domain.usecases.GetDataFromPaginationTaskUseCase
import com.example.internshipapp.domain.usecases.GetFavouritePostsUseCase
import com.example.internshipapp.domain.usecases.GetPostsFromNetworkUseCase
import com.example.internshipapp.domain.usecases.feature13.GetMusicPlayerStateUseCase
import com.example.internshipapp.domain.usecases.feature13.OnPlayOrPauseStateChangeUseCase
import com.example.internshipapp.domain.usecases.feature14.DownloadFileUseCase
import com.example.internshipapp.presentation.feature1.LoginViewModel
import com.example.internshipapp.presentation.feature12.MediaPlayerViewModel
import com.example.internshipapp.presentation.feature13.MusicPlayerViewModel
import com.example.internshipapp.presentation.feature14.RetrofitDownloadingViewModel
import com.example.internshipapp.presentation.feature2.PostWithCommentsViewModel
import com.example.internshipapp.presentation.feature2.PostsViewModel
import com.example.internshipapp.presentation.feature5.ConsumersViewModel
import com.example.internshipapp.presentation.feature6.PaginationViewModel
import com.example.internshipapp.presentation.feature8.NavigationTestViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    //useCases
    factory<GetDataFromPaginationTaskUseCase> {
        GetDataFromPaginationTaskUseCase(
            iPaginationRepository = get<IPaginationRepository>()
        )
    }
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

    //feature13
    factory<GetMusicPlayerStateUseCase> {
        GetMusicPlayerStateUseCase(
            iMusicPlayerRepository = get<IMusicPlayerRepository>()
        )
    }
    factory<OnPlayOrPauseStateChangeUseCase> {
        OnPlayOrPauseStateChangeUseCase(
            iMusicPlayerRepository = get<IMusicPlayerRepository>()
        )
    }
    factory<DownloadFileUseCase> {
        DownloadFileUseCase(iDownLoadFileRepository = get<IDownLoadFileRepository>())
    }

    //repositories
    single<IAuthRepository> {
        AuthRepositoryImpl()
    }
    single<IPaginationRepository> {
        PaginationRepository()
    }
    single<ILocalPostRepository> {
        PostLocalRepository(postsDao = get<PostsDao>())
    }
    single<IPostAndCommentsRemoteRepository> {
        PostAndCommentsRemoteRepositoryImpl(apiService = get<ApiService>())
    }
    single<IConsumersRepository> {
        ConsumersRepositoryImpl(
            consumersDao = get<ConsumersDao>()
        )
    }
    single<IMusicPlayerRepository> {
        MusicPlayerRepositoryImpl(
            application = androidApplication()
        )
    }
    //feature14
    single<IDownLoadFileRepository> {
        DownLoadFileRepositoryImpl(
            apiService = get<ApiService>(),
            app = androidApplication()
        )
    }

    viewModel<PaginationViewModel> {
        PaginationViewModel(
            getDataFromPaginationTaskUseCase = get<GetDataFromPaginationTaskUseCase>()
        )
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
    viewModel<ConsumersViewModel> {
        ConsumersViewModel(
            iConsumersRepository = get<IConsumersRepository>()
        )
    }
    single<PostsDao> {
        PostsDatabase.getInstance(application = androidApplication()).getPostsDao()
    }
    single<ApiService> {
        ApiFactory.apiService(application = androidApplication())
    }

    viewModel<NavigationTestViewModel> {
        NavigationTestViewModel()
    }

    viewModel<MediaPlayerViewModel> {
        MediaPlayerViewModel(
            player = ExoPlayer.Builder(androidApplication())
                .build()
        )
    }
    viewModel<MusicPlayerViewModel> {
        MusicPlayerViewModel(
            getMusicPlayerStateUseCase = get<GetMusicPlayerStateUseCase>(),
            onPlayOrPauseStateChangeUseCase = get<OnPlayOrPauseStateChangeUseCase>()
        )
    }
    viewModel<RetrofitDownloadingViewModel> {
        RetrofitDownloadingViewModel(downloadFileUseCase = get<DownloadFileUseCase>())
    }
}

