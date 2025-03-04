package com.example.internshipapp.presentation.feature11

import PermissionLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.internshipapp.myLog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph
@Destination
@Composable
fun ShowNotificationScreen() {

    val requestPostNotification = PermissionLauncher.build(
        permission = PermissionLauncher.POST_NOTIFICATIONS,
        onSuccess = { myLog("POST_NOTIFICATIONS: успех") },
        onFailure = { myLog("POST_NOTIFICATIONS: отклонено") }
    )
    val requestWriteStorage = PermissionLauncher.build(
        permission = PermissionLauncher.WRITE_EXTERNAL_STORAGE,
        onSuccess = { myLog("WRITE_EXTERNAL_STORAGE: успех") },
        onFailure = { myLog("WRITE_EXTERNAL_STORAGE: отклонено") }
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Button(
                onClick = { requestPostNotification.launch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Разрешение на Уведомления")
            }

            Button(
                onClick = { requestWriteStorage.launch() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Разрешение на Запись")
            }
        }
    }
}
