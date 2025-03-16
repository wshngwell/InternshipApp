package com.example.internshipapp.presentation.feature14

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.DownLoadingFileStateResultEntity
import com.example.internshipapp.domain.usecases.feature14.DownloadFileUseCase
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RetrofitDownloadingViewModel(
    private val downloadFileUseCase: DownloadFileUseCase
) : ViewModel() {

    data class State(
        val loadingStateFilesDir: String = "Загружено 0",
        val loadingStateExternalCacheDir: String = "Загружено 0",
        val loadingStatePublicDirectory: String = "Загружено 0",
        val loadingStatePicturePublicDirectory: String = "Загружено 0",
    )

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    sealed interface Intent {
        data class DownLoadFIleToFilesDir(
            val urlOfFile: String,
            val placeOfDownloading: String,
            val nameOfFile: String
        ) : Intent

        data class DownLoadFIleToExternalCacheDir(
            val urlOfFile: String,
            val placeOfDownloading: String,
            val nameOfFile: String
        ) : Intent

        data class DownLoadFIleToPublicDirectory(
            val urlOfFile: String,
            val placeOfDownloading: String,
            val nameOfFile: String
        ) : Intent

        data class DownLoadPictureToPublicDirectory(
            val urlOfFile: String,
            val placeOfDownloading: String,
            val nameOfFile: String
        ) : Intent


    }

    sealed interface Event {

        data object OnDownloadSuccess : Event
        data object Error : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.DownLoadFIleToFilesDir -> {

                viewModelScope.launch {
                    downloadFileUseCase(
                        intent.urlOfFile,
                        intent.placeOfDownloading,
                        intent.nameOfFile
                    ).collect { downLoadingStateResult ->

                        when (downLoadingStateResult) {

                            is DownLoadingFileStateResultEntity.DownLoadingFile -> {
                                _state.update {
                                    it.copy(loadingStateFilesDir = "Загружается ${downLoadingStateResult.value}")
                                }
                            }

                            is DownLoadingFileStateResultEntity.Error -> {
                                _state.update {
                                    it.copy(loadingStateFilesDir = "Ошибка загрузки")
                                }
                                _event.emit(Event.Error)
                            }

                            is DownLoadingFileStateResultEntity.Success -> {
                                _state.update {
                                    it.copy(loadingStateFilesDir = "Загружено")
                                }
                                _event.emit(Event.OnDownloadSuccess)
                            }

                        }

                    }
                }

            }

            is Intent.DownLoadFIleToExternalCacheDir -> {
                viewModelScope.launch {
                    downloadFileUseCase(
                        intent.urlOfFile,
                        intent.placeOfDownloading,
                        intent.nameOfFile
                    ).collect { downLoadingStateResult ->

                        when (downLoadingStateResult) {

                            is DownLoadingFileStateResultEntity.DownLoadingFile -> {
                                _state.update {
                                    it.copy(loadingStateExternalCacheDir = "Загружается ${downLoadingStateResult.value}")
                                }
                            }

                            is DownLoadingFileStateResultEntity.Error -> {
                                _state.update {
                                    it.copy(loadingStateExternalCacheDir = "Ошибка загрузки")
                                }
                                _event.emit(Event.Error)
                            }

                            is DownLoadingFileStateResultEntity.Success -> {
                                _state.update {
                                    it.copy(loadingStateExternalCacheDir = "Загружено")
                                }
                                _event.emit(Event.OnDownloadSuccess)
                            }
                        }

                    }
                }
            }

            is Intent.DownLoadFIleToPublicDirectory -> {
                viewModelScope.launch {
                    downloadFileUseCase(
                        intent.urlOfFile,
                        intent.placeOfDownloading,
                        intent.nameOfFile
                    ).collect { downLoadingStateResult ->

                        when (downLoadingStateResult) {

                            is DownLoadingFileStateResultEntity.DownLoadingFile -> {
                                _state.update {
                                    it.copy(loadingStatePublicDirectory = "Загружается ${downLoadingStateResult.value}")
                                }
                            }

                            is DownLoadingFileStateResultEntity.Error -> {
                                _state.update {
                                    it.copy(loadingStatePublicDirectory = "Ошибка загрузки")
                                }
                                _event.emit(Event.Error)
                            }

                            is DownLoadingFileStateResultEntity.Success -> {
                                _state.update {
                                    it.copy(loadingStatePublicDirectory = "Загружено")
                                }
                                _event.emit(Event.OnDownloadSuccess)
                            }
                        }

                    }
                }

            }

            is Intent.DownLoadPictureToPublicDirectory -> {
                viewModelScope.launch {
                    downloadFileUseCase(
                        intent.urlOfFile,
                        intent.placeOfDownloading,
                        intent.nameOfFile,
                        true
                    ).collect { downLoadingStateResult ->

                        when (downLoadingStateResult) {

                            is DownLoadingFileStateResultEntity.DownLoadingFile -> {
                                _state.update {
                                    it.copy(loadingStatePicturePublicDirectory = "Загружается ${downLoadingStateResult.value}")
                                }
                            }

                            is DownLoadingFileStateResultEntity.Error -> {
                                _state.update {
                                    it.copy(loadingStatePicturePublicDirectory = "Ошибка загрузки")
                                }
                                _event.emit(Event.Error)
                            }

                            is DownLoadingFileStateResultEntity.Success -> {
                                _state.update {
                                    it.copy(loadingStatePicturePublicDirectory = "Загружено")
                                }
                                _event.emit(Event.OnDownloadSuccess)
                            }

                        }

                    }
                }
            }
        }
    }
}