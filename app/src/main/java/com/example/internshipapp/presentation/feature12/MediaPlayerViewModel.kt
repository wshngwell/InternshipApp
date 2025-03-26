package com.example.internshipapp.presentation.feature12

import android.app.Application
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication

class MediaPlayerViewModel(
    application: Application
) : ViewModel() {


    private val videoUrl: String =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    data class State(
        val player: Player,
        val isPlaying: Boolean = false,
        val currentPositionOfSlider: Long = 0L,
        val isSliderDragging: Boolean = false,
        val buffered: Boolean = false,
        val videoDuration: Long = 1L,
        val shouldControlsButtonsBeVisible: Boolean = true
    ) {}

    private val _state = MutableStateFlow(
        State(
            player = ExoPlayer.Builder(application)
                .build()
        )
    )

    val state: StateFlow<State> = _state.asStateFlow()

    sealed interface Intent {
        data object Play : Intent
        data object Pause : Intent
        data object IsPlayingChange : Intent
        data class ChangeIsSliderDragging(val value: Boolean) : Intent
        data class UpdateShouldControlsButtonsBeVisible(val value: Boolean) : Intent
        data class UpdateCurrentPositionOfSlider(val value: Long) : Intent
    }

    fun sendIntent(intent: Intent) {
        when (intent) {

            Intent.IsPlayingChange -> {
                _state.update { it.copy(isPlaying = !it.isPlaying) }
            }

            Intent.Pause -> {
                _state.value.player?.pause()
            }

            Intent.Play -> {
                _state.value.player?.play()
            }

            is Intent.UpdateShouldControlsButtonsBeVisible -> {
                _state.update { it.copy(shouldControlsButtonsBeVisible = intent.value) }
            }

            is Intent.UpdateCurrentPositionOfSlider -> {
                _state.update {

                    it.copy(
                        currentPositionOfSlider = intent.value
                    )
                }
            }

            is Intent.ChangeIsSliderDragging -> {
                if (!intent.value) state.value.player?.seekTo(state.value.currentPositionOfSlider)
                _state.update { it.copy(isSliderDragging = intent.value) }
            }
        }
    }


    init {
        state.value.player?.prepare()
        state.value.player?.setMediaItem(
            MediaItem.fromUri(Uri.parse(videoUrl))
        )

        viewModelScope.launch {

            while (isActive) {
                if (!state.value.isSliderDragging) {
                    _state.update {
                        it.copy(
                            currentPositionOfSlider =
                            state.value.player.currentPosition
                        )
                    }
                }

                delay(200)
            }
        }


        val listener = object : Player.Listener {

            override fun onEvents(
                player: Player,
                events: Player.Events
            ) {

                super.onEvents(player, events)
                _state.update {
                    it.copy(
                        videoDuration = player.duration.coerceAtLeast(1L),
                        buffered = player.playbackState == Player.STATE_BUFFERING,
                        isPlaying = player.isPlaying
                    )
                }

            }
        }
        state.value.player?.addListener(listener)

    }

    override fun onCleared() {
        state.value.player?.release()
        super.onCleared()
    }
}