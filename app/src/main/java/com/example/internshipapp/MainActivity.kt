package com.example.internshipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.internshipapp.presentation.NavGraphs
import com.example.internshipapp.ui.theme.InternshipAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

