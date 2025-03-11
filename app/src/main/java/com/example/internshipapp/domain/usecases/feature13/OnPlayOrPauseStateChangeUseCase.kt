package com.example.internshipapp.domain.usecases.feature13

import com.example.internshipapp.domain.repositories.IMusicPlayerRepository

class OnPlayOrPauseStateChangeUseCase(
    private val iMusicPlayerRepository: IMusicPlayerRepository
) {
    operator fun invoke() = iMusicPlayerRepository.playOrPauseStateChange()
}