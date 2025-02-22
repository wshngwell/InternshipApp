package com.example.internshipapp.presentation

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.navigation.Screen
import com.example.internshipapp.navigation.Screen.Companion.KEY_POST
import com.example.internshipapp.presentation.feature1.AfterAuthorizationScreen
import com.example.internshipapp.presentation.feature1.LoginScreen
import com.example.internshipapp.presentation.feature2.DetailedPostWithCommentsScreen
import com.example.internshipapp.presentation.feature2.PostsScreen
import com.example.internshipapp.presentation.feature5.ConsumersScreen
import com.google.gson.Gson

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
        composable(
            route = Screen.PostScreen.route
        ) {
            PostsScreen(navController = navController)
        }
        composable(
            route = Screen.ConsumersScreen.route,
        ) {
            ConsumersScreen(navController = navController)
        }
        composable(
            route = Screen.DetailedPostScreenWithComments.route,
            arguments = listOf(
                navArgument(KEY_POST) {
                    type = NavType.StringType
                }
            )
        ) {
            val encodedJsonPost = it.arguments?.getString(KEY_POST) ?: ""
            val jsonPost = Uri.decode(encodedJsonPost)
            val postEntity = Gson().fromJson(jsonPost, PostEntity::class.java)
            DetailedPostWithCommentsScreen(
                postEntity = postEntity
            )

        }

    }
}
