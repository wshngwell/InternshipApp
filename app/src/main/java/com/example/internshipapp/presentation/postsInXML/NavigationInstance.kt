package com.example.internshipapp.presentation.postsInXML

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

data class NavigationInstance(
    val id: Int,
    val bundle: Bundle? = null
) {
    companion object {
        fun NavController.myNavigate(instance: NavigationInstance, isSingle: Boolean = true) {
            this.navigate(
                instance.id,
                instance.bundle,
                NavOptions.Builder().apply {
                    if (isSingle) {
                        setPopUpTo(instance.id, true)
                        setLaunchSingleTop(true)
                    }
                }.build()
            )
        }
    }

}