package com.example.internshipapp.presentation.feature6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.usecases.GetDataFromPaginationTaskUseCase
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaginationViewModel(
    private val getDataFromPaginationTaskUseCase: GetDataFromPaginationTaskUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()


    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow

    data class State(
        val dataList: List<String> = listOf(),
        val currentPage: Int = 1,
        val maxPages: Int = 1,
        val firstElementOfLastLoadedList: String = "",
        val isLoading: Boolean = false,
        val shouldBeLoadAgain: Boolean = true
    )


    sealed interface Intent {
        data object OnLoadNextPage : Intent
    }

    sealed interface Event {}

    init {
        viewModelScope.launch {
            loadDataWithCurrentPage()
        }
    }

    private suspend fun loadDataWithCurrentPage() {

        while (_state.value.shouldBeLoadAgain
            && _state.value.currentPage <= state.value.maxPages
        ) {
            _state.update { it.copy(isLoading = true) }

            val listResult = getDataFromPaginationTaskUseCase(state.value.currentPage)
            listResult.onSuccess { myDataPageEntity ->
                myLog("Success")
                _state.update {
                    it.copy(
                        dataList = it.dataList + myDataPageEntity.data,
                        firstElementOfLastLoadedList = myDataPageEntity.data[0],
                        maxPages = myDataPageEntity.maxPage,
                        currentPage = it.currentPage + 1,
                        shouldBeLoadAgain = false,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            Intent.OnLoadNextPage -> {
                if (!state.value.isLoading) {
                    _state.update { it.copy(shouldBeLoadAgain = true) }
                    viewModelScope.launch {
                        loadDataWithCurrentPage()
                    }

                }
            }
        }
    }

}