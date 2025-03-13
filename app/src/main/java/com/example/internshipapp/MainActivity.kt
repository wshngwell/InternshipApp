package com.example.internshipapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.internshipapp.data.feature13.MusicForegroundService
import com.example.internshipapp.domain.repositories.IMusicPlayerRepository
import com.example.internshipapp.domain.usecases.feature13.GetMusicPlayerStateUseCase
import com.example.internshipapp.presentation.NavGraphs
import com.example.internshipapp.presentation.feature13.MusicPlayerViewModel
import com.example.internshipapp.ui.theme.InternshipAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     /*   val repository by inject<IMusicPlayerRepository>()
        val intent = Intent(this, MusicForegroundService::class.java)
        lifecycleScope.launch {
            repository.musicState.map { it.isPlayingState }.distinctUntilChanged().collect {
                if (it) {
                    stopService(intent)
                    startService(intent)
                } else {
                    stopService(intent)
                }
            }
        }*/

        setContent {
            InternshipAppTheme {
                Scaffold { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        val navHostEngine = rememberNavHostEngine(
                            rootDefaultAnimations = RootNavGraphDefaultAnimations(
                                enterTransition = { slideInHorizontally { it } },
                                exitTransition = { slideOutHorizontally { -it } },
                                popExitTransition = { slideOutHorizontally { it } },
                                popEnterTransition = { slideInHorizontally { -it } }

                            )
                        )
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            engine = navHostEngine
                        )
                    }
                }
            }
        }
    }
}

