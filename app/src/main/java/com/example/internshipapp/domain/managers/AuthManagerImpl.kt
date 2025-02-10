package com.example.internshipapp.domain.managers

import com.example.internshipapp.domain.entities.UserEntity
import com.example.internshipapp.domain.repositories.IAuthRepository

data class AuthManagerImpl(
    private val repository: IAuthRepository
) : IManager {
    override suspend fun checkIfUserRegistered(userEntity: UserEntity) = repository.checkIsUserRegistered(userEntity)

}
