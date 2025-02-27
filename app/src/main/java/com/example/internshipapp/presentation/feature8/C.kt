package com.example.internshipapp.presentation.feature8

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.internshipapp.presentation.NavGraphs
import com.example.internshipapp.presentation.feature8.navigation.MyNestedNavGraph
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator

@MyNestedNavGraph
@Destination
@Composable
fun C(
    navigator: DestinationsNavigator,
    navController: NavController,
    resultNavigator: ResultBackNavigator<String>
) {
    val viewModel = navController.sharedViewModel<NavigationTestViewModel>()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 50.dp, end = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                style = TextStyle(color = Color.Magenta),
                text = "Экран C"
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.popBackStack(NavGraphs.myNested, inclusive = true) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Return to A screen"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { resultNavigator.navigateBack(result = randomString(10)) },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Return to B screen with result"
                )
            }
        }
    }
}

fun randomString(length: Int): String {
    val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}