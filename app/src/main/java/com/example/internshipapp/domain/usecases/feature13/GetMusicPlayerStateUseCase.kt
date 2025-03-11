package com.example.internshipapp.domain.usecases.feature13

import com.example.internshipapp.domain.repositories.IMusicPlayerRepository

class GetMusicPlayerStateUseCase(
    private val iMusicPlayerRepository: IMusicPlayerRepository
) {
    operator fun invoke() = iMusicPlayerRepository.musicState
}