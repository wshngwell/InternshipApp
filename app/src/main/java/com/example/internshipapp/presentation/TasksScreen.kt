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
import androidx.navigation.NavController
import com.example.internshipapp.navigation.Screen
import com.example.internshipapp.navigation.myNavigate
import com.example.internshipapp.ui.theme.defaultButtonTextSp

@Composable
fun TasksScreen(
    navController: NavController
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
                onClick = { navController.myNavigate(Screen.AuthorizationScreen.getAuthorizationScreenRoute()) },
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
                onClick = { navController.myNavigate(Screen.PostScreen.getPostScreenRoute()) },
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
        }

    }
}
