package com.example.internshipapp.domain.usecases

import com.example.internshipapp.domain.repositories.IPaginationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetDataFromPaginationTaskUseCase(
    private val iPaginationRepository: IPaginationRepository
) {
    suspend operator fun invoke(page: Int) = withContext(Dispatchers.IO){
        iPaginationRepository.getMyDataPage(page)
    }

}