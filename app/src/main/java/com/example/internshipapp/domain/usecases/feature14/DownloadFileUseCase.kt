package com.example.internshipapp.domain.usecases.feature14

import com.example.internshipapp.domain.repositories.IDownLoadFileRepository

class DownloadFileUseCase(
    private val iDownLoadFileRepository: IDownLoadFileRepository
) {
    operator fun invoke(
        urlOfFile: String,
        placeOfDownloading: String,
        nameOfFile: String,
        shouldBeScanned: Boolean = false
    ) = iDownLoadFileRepository.downLoadFile(
        urlOfFile,
        placeOfDownloading,
        nameOfFile,
        shouldBeScanned
    )
}