package com.example.internshipapp.domain.repositories

import com.example.internshipapp.domain.entities.MusicPlayerStateEntity
import kotlinx.coroutines.flow.StateFlow

interface IMusicPlayerRepository {
    val musicState: StateFlow<MusicPlayerStateEntity>
    fun playOrPauseStateChange()
}