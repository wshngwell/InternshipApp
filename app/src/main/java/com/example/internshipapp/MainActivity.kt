package com.example.internshipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.internshipapp.presentation.MainScreen
import com.example.internshipapp.ui.theme.InternshipAppTheme
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            InternshipAppTheme {
                MainScreen()
            }
        }
    }
}

