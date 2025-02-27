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
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.feature8.destinations.CDestination
import com.example.internshipapp.presentation.feature8.navigation.MyNestedNavGraph
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@MyNestedNavGraph(start = true)
@Destination
@Composable
fun B(
    navigator: DestinationsNavigator,
    navController: NavController,
    resultRecipient: ResultRecipient<CDestination, String>
) {

    val viewModel = navController.sharedViewModel<NavigationTestViewModel>()

    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> {

            }

            is NavResult.Value -> {
                myLog(result.value)
            }
        }
    }

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
                text = "Экран B"
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navigator.navigate(CDestination)
                },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Navigate to C screen"
                )
            }
        }
    }
}