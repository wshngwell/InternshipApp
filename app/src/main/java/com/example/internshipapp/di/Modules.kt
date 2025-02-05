package com.example.internshipapp.di

import com.example.internshipapp.data.RemoteRepository
import com.example.internshipapp.domain.manager.IManager
import com.example.internshipapp.domain.manager.Manager
import com.example.internshipapp.domain.repositories.IRemoteRepository
import com.example.internshipapp.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {

    single<IRemoteRepository> {
        RemoteRepository
    }
    single<IManager> {
        Manager(repository = get<IRemoteRepository>())
    }
    viewModel {
        LoginViewModel(manager = get<IManager>())
    }
}