package com.example.internshipapp.domain.entities

import java.io.File

sealed class DownLoadingFileStateResultEntity {

    data class DownLoadingFile(val value: Float) : DownLoadingFileStateResultEntity()
    data class Error(val t: Throwable) : DownLoadingFileStateResultEntity()
    class Success(val file: File) : DownLoadingFileStateResultEntity()
}