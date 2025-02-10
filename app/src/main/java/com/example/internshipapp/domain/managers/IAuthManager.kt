package com.example.internshipapp.domain.managers

import com.example.internshipapp.domain.entities.UserEntity

interface IManager {
    suspend fun checkIfUserRegistered(userEntity: UserEntity): Boolean
}