package com.example.internshipapp.data.local.dbModels

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumersDao {
    @Query("SELECT * FROM Consumer_table")
    fun getConsumersList(): Flow<List<ConsumerDbModel>>

    @Insert
    fun addConsumerToDb(consumerDbModel: ConsumerDbModel)

    @Query("DELETE FROM Consumer_table WHERE id =:consumerId")
    fun removeConsumerFromDb(consumerId: Int)
}