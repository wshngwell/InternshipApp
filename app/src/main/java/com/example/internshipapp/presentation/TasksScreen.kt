package com.example.internshipapp.presentation

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
import androidx.compose.ui.unit.dp
import com.example.internshipapp.presentation.destinations.ADestination
import com.example.internshipapp.presentation.destinations.ConsumersScreenDestination
import com.example.internshipapp.presentation.destinations.LoginScreenDestination
import com.example.internshipapp.presentation.destinations.PaginationScreenDestination
import com.example.internshipapp.presentation.destinations.PostsScreenDestination
import com.example.internshipapp.presentation.destinations.TwoBoxesTaskDestination
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun TasksScreen(
    navigator: DestinationsNavigator
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(start = 50.dp, end = 50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(LoginScreenDestination) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Task1"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(PostsScreenDestination()) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Task2"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(ConsumersScreenDestination) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "ConsumersList"
                )
            }


            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(PaginationScreenDestination) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "PaginationTask"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(TwoBoxesTaskDestination) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "TwoBoxesTask"
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.navigate(ADestination) },
                colors = ButtonColors(
                    containerColor = Color.Magenta,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Compose Destionations Task"
                )
            }
        }

    }
}
