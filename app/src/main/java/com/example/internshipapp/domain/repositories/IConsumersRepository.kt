package com.example.internshipapp.domain.repositories

import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import kotlinx.coroutines.flow.SharedFlow

interface IConsumersRepository {
    val consumersList: SharedFlow<List<ConsumerDbModel>>

    suspend fun addConsumerToDb(consumerDbModel: ConsumerDbModel)
    suspend fun removeConsumerFromDb(consumerId: Int)
}