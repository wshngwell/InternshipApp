package com.example.internshipapp.presentation.feature13

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.domain.usecases.feature13.GetMusicPlayerStateUseCase
import com.example.internshipapp.domain.usecases.feature13.OnPlayOrPauseStateChangeUseCase
import com.example.internshipapp.myLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicPlayerViewModel(
    private val getMusicPlayerStateUseCase: GetMusicPlayerStateUseCase,
    private val onPlayOrPauseStateChangeUseCase: OnPlayOrPauseStateChangeUseCase
) : ViewModel() {


    data class State(
        val isPlayingState: Boolean = false,
    )

    private val _state = MutableStateFlow(State())

    val state = _state.asStateFlow()

    sealed interface Intent {
        data object OnPlayOrPauseStateChange : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {
            Intent.OnPlayOrPauseStateChange -> {
                onPlayOrPauseStateChangeUseCase()
            }
        }
    }

    init {
        viewModelScope.launch {
            getMusicPlayerStateUseCase().collect { musicState ->
                myLog("COLLECT")
                _state.update {
                    it.copy(
                        isPlayingState = musicState.isPlayingState,
                    )
                }

            }
        }
    }
}