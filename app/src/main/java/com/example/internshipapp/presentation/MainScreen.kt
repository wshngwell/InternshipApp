package com.example.internshipapp.presentation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.internshipapp.navigation.Screen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.TasksScreen.route
    ) {
        composable(
            route = Screen.AuthorizationScreen.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.TasksScreen.route
        ) {
            TasksScreen(navController = navController)
        }
        composable(
            route = Screen.AfterAuthorizationScreen.route
        ) {
            AfterAuthorizationScreen(navController)
        }
    }
}
