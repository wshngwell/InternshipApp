package com.example.internshipapp.data

import com.example.internshipapp.domain.entities.User
import com.example.internshipapp.domain.repositories.IRemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object RemoteRepository : IRemoteRepository {

    private val usersMap = listOf(
        UserDbModel("User1", "12345678"),
        UserDbModel("User2", "11111111"),
        UserDbModel("User3", "22222222")
    )

    override suspend fun checkIsUserRegistered(user: User): Boolean = withContext(Dispatchers.IO) {
        return@withContext usersMap.find {
            user.login == it.login && user.password == it.password
        } != null
    }
}