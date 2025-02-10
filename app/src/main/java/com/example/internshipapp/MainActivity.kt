package com.example.internshipapp

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.internshipapp.presentation.MainScreen
import com.example.internshipapp.ui.theme.InternshipAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InternshipAppTheme {
                Scaffold { paddingValues ->
                    Column(modifier = Modifier.padding(paddingValues)) {
                        MainScreen()
                    }
                }
            }
        }
    }
}

