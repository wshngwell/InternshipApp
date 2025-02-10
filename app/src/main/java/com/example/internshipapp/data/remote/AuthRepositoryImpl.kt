package com.example.internshipapp.data.remote

import com.example.internshipapp.data.local.UserDbModel
import com.example.internshipapp.domain.entities.UserEntity
import com.example.internshipapp.domain.repositories.IAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl: IAuthRepository {

    private val usersMap = listOf(
        UserDbModel("User1", "12345678"),
        UserDbModel("User2", "11111111"),
        UserDbModel("User3", "22222222")
    )

    override suspend fun checkIsUserRegistered(userEntity: UserEntity): Boolean = withContext(Dispatchers.IO) {
        return@withContext usersMap.find {
            userEntity.login == it.login && userEntity.password == it.password
        } != null
    }

}