package com.example.internshipapp.presentation.feature8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import com.example.internshipapp.presentation.ui.theme.InternshipAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine

class TryComposeDestinationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternshipAppTheme {

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