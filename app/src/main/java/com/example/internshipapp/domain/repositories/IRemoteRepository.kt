package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.User

interface IRemoteRepository {
    suspend fun checkIsUserRegistered(user: User):Boolean
}