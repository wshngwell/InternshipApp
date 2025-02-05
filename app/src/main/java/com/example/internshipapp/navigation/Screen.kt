package com.example.internshipapp.navigation

sealed class Screen(val route: String) {
    data object TasksScreen : Screen(route = TASKS_SCREEN)
    data object AuthorizationScreen : Screen(AUTHORIZATION_SCREEN)
    data object AfterAuthorizationScreen : Screen(AFTER_AUTHORIZATION_SCREEN)


    companion object {
        private val TASKS_SCREEN = "TASKS_SCREEN"
        private val AUTHORIZATION_SCREEN = "AUTHORIZATION_SCREEN"
        private val AFTER_AUTHORIZATION_SCREEN = "AFTER_AUTHORIZATION_SCREEN"
    }
}