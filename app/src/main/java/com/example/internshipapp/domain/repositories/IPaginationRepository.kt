package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.MyDataPageEntity

interface IPaginationRepository {
    suspend fun getMyDataPage(page: Int): Result<MyDataPageEntity>
}