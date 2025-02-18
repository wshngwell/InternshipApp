package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.entities.UserEntity
import com.example.internshipapp.domain.repositories.IAuthRepository

class AuthUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(userEntity: UserEntity) =
        repository.checkIsUserRegistered(userEntity)

}
