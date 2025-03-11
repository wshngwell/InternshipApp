package com.example.internshipapp.presentation.feature13

import PermissionLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.feature13.MusicPlayerViewModel.Intent
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import org.koin.androidx.compose.koinViewModel


@RootNavGraph
@Destination
@Composable
fun MusicPLayerScreen() {
    val viewModel = koinViewModel<MusicPlayerViewModel>()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }

    UI(
        intent = intent
    )
}

@Preview
@Composable
private fun UI(
    intent: (Intent) -> Unit = {}
) {
    val requestPostNotification = PermissionLauncher.build(
        permission = PermissionLauncher.POST_NOTIFICATIONS,
        onSuccess = { myLog("POST_NOTIFICATIONS: успех") },
        onFailure = { myLog("POST_NOTIFICATIONS: отклонено") }
    )
    SideEffect {
        requestPostNotification.launch()
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                intent(Intent.OnPlayOrPauseStateChange)
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
                text = "Play/Pause"
            )
        }
    }

}