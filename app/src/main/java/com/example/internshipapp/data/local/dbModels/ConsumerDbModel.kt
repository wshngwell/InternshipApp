package com.example.internshipapp.data.local.dbModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Consumer_table")
data class ConsumerDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val A: String,
    val B: String,
    val C: String
)