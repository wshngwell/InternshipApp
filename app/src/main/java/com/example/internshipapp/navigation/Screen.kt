package com.example.internshipapp.navigation

import android.net.Uri
import com.example.internshipapp.domain.entities.PostEntity
import com.google.gson.Gson

sealed class Screen(val route: String) {

    data object TasksScreen : Screen(route = TASKS_SCREEN) {
        fun getTaskScreenRoute(): String = route
    }

    data object AuthorizationScreen : Screen(AUTHORIZATION_SCREEN) {
        fun getAuthorizationScreenRoute(): String = route
    }

    data object AfterAuthorizationScreen : Screen(AFTER_AUTHORIZATION_SCREEN) {
        fun getAfterAuthorizationScreenRoute(): String = route
    }

    data object PostScreen : Screen(POST_SCREEN) {
        fun getPostScreenRoute(): String = route
    }

    data object PaginationScreen : Screen(PAGINATION_SCREEN) {
        fun getPaginationScreen(): String = route
    }

    data object DetailedPostScreenWithComments :
        Screen(DETAILED_POST_SCREEN_WITH_COMMENTS_WITH_ARGS) {

        fun getRouteWithPost(postEntity: PostEntity): String {
            val gsonPost = Uri.encode(Gson().toJson(postEntity))
            return DETAILED_POST_SCREEN_WITH_COMMENTS + "/${gsonPost}"
        }
    }


    companion object {
        private const val TASKS_SCREEN = "TASKS_SCREEN"
        private const val AUTHORIZATION_SCREEN = "AUTHORIZATION_SCREEN"
        private const val AFTER_AUTHORIZATION_SCREEN = "AFTER_AUTHORIZATION_SCREEN"
        private const val POST_SCREEN = "POST_SCREEN"
        private const val PAGINATION_SCREEN = "PAGINATION_SCREEN"

        const val KEY_POST = "post_id"
        private const val DETAILED_POST_SCREEN_WITH_COMMENTS =
            "DETAILED_POST_SCREEN_WITH_COMMENTS"
        private const val DETAILED_POST_SCREEN_WITH_COMMENTS_WITH_ARGS =
            "DETAILED_POST_SCREEN_WITH_COMMENTS/{$KEY_POST}"
    }
}