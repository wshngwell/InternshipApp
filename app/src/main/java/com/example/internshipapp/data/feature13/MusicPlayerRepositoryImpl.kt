package com.example.internshipapp.data.feature13

import android.app.Application
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.internshipapp.domain.entities.MusicPlayerStateEntity
import com.example.internshipapp.domain.repositories.IMusicPlayerRepository
import com.example.internshipapp.myLog
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MusicPlayerRepositoryImpl(
    private val application: Application
) : IMusicPlayerRepository {


    private val _musicState: MutableStateFlow<MusicPlayerStateEntity> = MutableStateFlow(
        MusicPlayerStateEntity(
            isPlayingState = false,
            currentPosition = 0L,
            musicDuration = 0L,
        )
    )
    override val musicState: StateFlow<MusicPlayerStateEntity>
        get() = _musicState.asStateFlow()


    private val exoPlayer by lazy {
        ExoPlayer.Builder(application).build()
            .apply {
                prepare()
                setMediaItem(
                    MediaItem.fromUri(Uri.parse(musicUrl))
                )
                addListener(object : Player.Listener {

                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)
                        _musicState.update {
                            it.copy(
                                musicDuration = player.duration,
                                isPlayingState = isPlaying
                            )
                        }

                    }
                })

            }
    }

    override fun playOrPauseStateChange() {
        if (musicState.value.isPlayingState) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
        _musicState.update {
            it.copy(isPlayingState = !musicState.value.isPlayingState)
        }
    }

    init {
        MainScope().launch {
            while (isActive) {
                delay(1000)

                val currentPosition = exoPlayer.currentPosition
                myLog(currentPosition.toString())

                _musicState.update {
                    it.copy(currentPosition = currentPosition)
                }
            }
        }
    }

    companion object {
        private const val musicUrl = "https://samplelib.com/lib/preview/mp3/sample-15s.mp3"
    }
}