package com.example.internshipapp.data.remote.feature6

import com.example.internshipapp.domain.entities.MyDataPageEntity
import com.example.internshipapp.domain.repositories.IPaginationRepository
import com.example.internshipapp.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class PaginationRepository : IPaginationRepository {
    override suspend fun getMyDataPage(page: Int): Result<MyDataPageEntity> =
        withContext(Dispatchers.IO)
        {
            delay(if (page == 1) 0 else 4_000)
            return@withContext runCatching {
                if (Random.nextBoolean()) {
                    (0..10).map { "${page}-${it}" }.let {
                        MyDataPageEntity(
                            data = it,
                            maxPage = 5
                        )
                    }
                } else {
                    myLog("Ошибка")
                    throw Exception("server error")
                }
            }
        }
}