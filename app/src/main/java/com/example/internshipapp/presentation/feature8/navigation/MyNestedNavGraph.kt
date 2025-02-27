package com.example.internshipapp.presentation.feature8.navigation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@NavGraph
annotation class MyNestedNavGraph(
    val start: Boolean = false
)
