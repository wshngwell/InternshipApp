package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.UserEntity

interface IAuthRepository {
    suspend fun checkIsUserRegistered(userEntity: UserEntity): Boolean
}