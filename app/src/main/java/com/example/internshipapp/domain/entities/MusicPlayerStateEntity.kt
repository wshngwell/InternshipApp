package com.example.internshipapp.domain.entities


data class MusicPlayerStateEntity(
    val isPlayingState: Boolean,
    val currentPosition: Long,
    val musicDuration: Long,
)