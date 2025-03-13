package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.DownLoadingFileStateResultEntity
import kotlinx.coroutines.flow.Flow

interface IDownLoadFileRepository {

     fun downLoadFile(
        urlOfFile: String,
        placeOfDownloading: String,
        nameOfFile: String,
        shouldBeScanned:Boolean
    ): Flow<DownLoadingFileStateResultEntity>

}