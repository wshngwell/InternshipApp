package com.example.internshipapp.navigation

import androidx.navigation.NavController
import androidx.navigation.navOptions

fun NavController.myNavigate(
    route: String,
    isSingle: Boolean = true
) {
    this.navigate(route, navOptions {
        if (isSingle) {
            popUpTo(route) {
                inclusive = true
            }
            launchSingleTop = true
        }
    })
}
