package com.example.internshipapp.domain.manager

import com.example.internshipapp.domain.entities.User

interface IManager {

    suspend fun checkIfUserRegistered(user: User): Boolean
}