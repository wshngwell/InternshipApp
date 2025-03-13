package com.example.internshipapp.data.feature14

import android.app.Application
import com.example.internshipapp.data.remote.ApiService
import com.example.internshipapp.data.remote.parseToLoadingException
import com.example.internshipapp.domain.entities.DownLoadingFileStateResultEntity
import com.example.internshipapp.domain.repositories.IDownLoadFileRepository
import com.example.internshipapp.myLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import java.io.File

class DownLoadFileRepositoryImpl(
    private val apiService: ApiService,
    private val app: Application
) : IDownLoadFileRepository {


    override fun downLoadFile(
        urlOfFile: String,
        placeOfDownloading: String,
        nameOfFile: String,
        shouldBeScanned: Boolean
    ): Flow<DownLoadingFileStateResultEntity> = channelFlow {

        val tempFileDirectory = File(placeOfDownloading)
        val tempFile = File(tempFileDirectory.parentFile, "test.txt")

        runCatching {

            send(DownLoadingFileStateResultEntity.DownLoadingFile(value = 0f))

            tempFileDirectory.mkdirs()
            tempFile.createNewFile()

            val result = File(placeOfDownloading, nameOfFile)

            val responseBody = apiService.downLoadFile(urlOfFile)
            responseBody.byteStream().use { inputStream ->
                tempFile.outputStream().use { outputStream ->
                    val totalBytes = responseBody.contentLength().toFloat()
                    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                    var progressBytes = 0f
                    var bytes = inputStream.read(buffer)
                    while (bytes >= 0) {
                        outputStream.write(buffer, 0, bytes)
                        progressBytes += bytes
                        bytes = inputStream.read(buffer)
                        myLog("123")
                        send(
                            DownLoadingFileStateResultEntity.DownLoadingFile(
                                (progressBytes / totalBytes)
                            )
                        )
                    }
                }
            }
            tempFile.renameTo(result)
            if (shouldBeScanned) {
                SingleMediaScanner(app, result)
            }
            send(
                DownLoadingFileStateResultEntity.Success(
                    tempFile
                )
            )
        }
            .getOrElse {
                tempFile.delete()
                send(DownLoadingFileStateResultEntity.Error(it.parseToLoadingException()))
            }

    }
        .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
        .flatMapConcat {
            channelFlow {
                send(it)
                delay(200)
            }
        }
        .flowOn(Dispatchers.IO)

}