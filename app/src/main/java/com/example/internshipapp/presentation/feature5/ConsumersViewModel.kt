package com.example.internshipapp.presentation.feature5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.data.local.dbModels.ConsumerDbModel
import com.example.internshipapp.domain.repositories.IConsumersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConsumersViewModel(
    private val iConsumersRepository: IConsumersRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    data class State(
        val consumersList: List<ConsumerDbModel> = listOf()
    )

    sealed interface Intent {
        data object OnAddToFavourite : Intent
        data class OnRemoveFromFavourite(val consumer: ConsumerDbModel) : Intent
    }


    init {
        viewModelScope.launch {
            iConsumersRepository.consumersList.collect { consumersListFromDb ->
                _state.update { it.copy(consumersList = consumersListFromDb) }
            }
        }
    }

    fun sendIntent(intent: Intent) {
        when (intent) {

            is Intent.OnAddToFavourite -> {
                viewModelScope.launch {
                    iConsumersRepository.addConsumerToDb(
                        consumerDbModel = ConsumerDbModel(
                            id = 0,
                            B = "B",
                            C = "C",
                            D = "D"
                        )
                    )
                }
            }

            is Intent.OnRemoveFromFavourite -> {
                viewModelScope.launch {
                    iConsumersRepository.removeConsumerFromDb(intent.consumer.id)
                }

            }
        }
    }
}