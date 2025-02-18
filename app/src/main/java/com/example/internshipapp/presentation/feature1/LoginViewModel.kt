package com.example.internshipapp.presentation.feature1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.entities.UserEntity
import com.example.internshipapp.domain.usecases.AuthUseCase
import com.example.internshipapp.presentation.SingleFlowEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    data class State(
        val userEntity: UserEntity = UserEntity("", ""),
        val isloading: Boolean = false,

        ) {
        val enabledEnterButton: Boolean =
            userEntity.login.isNotEmpty() && userEntity.password.isNotEmpty()
    }

    private val _state = MutableStateFlow(
        State()
    )
    val state = _state.asStateFlow()

    sealed interface Event {

        data object OnLoginSuccess : Event
        data object Error : Event
    }

    private val _event = SingleFlowEvent<Event>(viewModelScope)
    val event = _event.flow


    sealed interface Intent {
        data class OnLoginTextChange(val login: String) : Intent
        data class OnPasswordTextChange(val password: String) : Intent
        data object CheckPasswordAndLogin : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            is Intent.CheckPasswordAndLogin -> {
                viewModelScope.launch {

                    _state.update { it.copy(isloading = true) }
                    delay(2000)
                    if (authUseCase(state.value.userEntity)) {
                        _event.emit(Event.OnLoginSuccess)
                        _state.update {
                            it.copy(isloading = false)
                        }
                    } else {
                        _event.emit(Event.Error)
                        _state.update {
                            it.copy(isloading = false)
                        }
                    }
                }
            }

            is Intent.OnLoginTextChange -> {
                _state.update { it.copy(userEntity = it.userEntity.copy(login = intent.login)) }
            }

            is Intent.OnPasswordTextChange -> {
                _state.update { it.copy(userEntity = state.value.userEntity.copy(password = intent.password)) }
            }
        }
    }
}