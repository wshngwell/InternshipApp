package com.example.internshipapp.data.local.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PostDbModel_table")
data class PostDbModel(
    val body: String,
    @PrimaryKey
    val id: Int,
    val title: String,
    val userId: Int,
    val timeOfInsertion: Long
)