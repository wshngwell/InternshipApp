package com.example.internshipapp.data.local

import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import com.example.internshipapp.data.local.dbModels.ConsumersDao
import com.example.internshipapp.domain.repositories.IConsumersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext

class ConsumersRepositoryImpl(
    private val consumersDao: ConsumersDao
) : IConsumersRepository {

    private val scope = CoroutineScope(Dispatchers.IO)

    override val consumersList = consumersDao.getConsumersList()
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .distinctUntilChanged()
        .shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 10_000),
            replay = 1
        )

    override suspend fun addConsumerToDb(consumerDbModel: ConsumerDbModel) =
        withContext(Dispatchers.IO) {
            consumersDao.addConsumerToDb(consumerDbModel)
        }


    override suspend fun removeConsumerFromDb(consumerId: Int) = withContext(Dispatchers.IO) {
        consumersDao.removeConsumerFromDb(consumerId)
    }
}