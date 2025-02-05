package com.example.internshipapp.domain.manager

import com.example.internshipapp.domain.entities.User
import com.example.internshipapp.domain.repositories.IRemoteRepository

data class Manager(
    private val repository: IRemoteRepository
) : IManager {
    override suspend fun checkIfUserRegistered(user: User) = repository.checkIsUserRegistered(user)

}
