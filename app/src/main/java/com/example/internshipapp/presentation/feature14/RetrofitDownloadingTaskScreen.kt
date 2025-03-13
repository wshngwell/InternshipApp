package com.example.internshipapp.presentation.feature14

import PermissionLauncher
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.internshipapp.data.MyFilesFolders
import com.example.internshipapp.myLog
import com.example.internshipapp.ui.theme.defaultButtonTextSp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import org.koin.androidx.compose.koinViewModel


@RootNavGraph
@Destination
@Composable
fun RetrofitDownLoadingFileScreen() {
    val viewModel = koinViewModel<RetrofitDownloadingViewModel>()
    val event: Flow<RetrofitDownloadingViewModel.Event> by remember { mutableStateOf(viewModel.event) }
    val intent: (RetrofitDownloadingViewModel.Intent) -> Unit by remember { mutableStateOf(viewModel::sendIntent) }

    val state by viewModel.state.collectAsStateWithLifecycle()

    UI(
        state = state,
        intent = intent,
        event = event
    )


}


@Preview
@Composable
private fun UI(
    state: RetrofitDownloadingViewModel.State = RetrofitDownloadingViewModel.State(""),
    intent: (RetrofitDownloadingViewModel.Intent) -> Unit = {},
    event: Flow<RetrofitDownloadingViewModel.Event> = flow { }
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        event.filterIsInstance<RetrofitDownloadingViewModel.Event>().collect {
            when (it) {
                RetrofitDownloadingViewModel.Event.Error -> {
                    Toast.makeText(
                        context,
                        "Ошибка загрузки файла",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                RetrofitDownloadingViewModel.Event.OnDownloadSuccess -> {
                    Toast.makeText(
                        context,
                        "Успешно загружено",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    intent(
                        RetrofitDownloadingViewModel.Intent.DownLoadFIleToFilesDir(
                            "http://lg.hosterby.com/10MB.test",
                            context.filesDir.absolutePath.toString() + "/La/Test",
                            "MyTest.test"
                        )
                    )
                },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "filesDir"
                )
            }
            Text(
                fontSize = defaultButtonTextSp,
                text = state.loadingStateFilesDir
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    intent(
                        RetrofitDownloadingViewModel.Intent.DownLoadFIleToExternalCacheDir(
                            "http://lg.hosterby.com/10MB.test",
                            context.externalCacheDir?.absolutePath.toString() + "/La/Test",
                            "MyTest.test"
                        )
                    )
                },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "externalCacheDir"
                )
            }
            Text(
                fontSize = defaultButtonTextSp,
                text = state.loadingStateExternalCacheDir
            )

            val requestPostNotification = PermissionLauncher.build(
                permission = PermissionLauncher.WRITE_EXTERNAL_STORAGE,
                onSuccess = {
                    intent(
                        RetrofitDownloadingViewModel.Intent.DownLoadFIleToPublicDirectory(
                            "https://speedtest.selectel.ru/100MB",
                            MyFilesFolders.fileBaseFolderForPublicDownloading,
                            "MyTest.test"
                        )
                    )
                },
                onFailure = { myLog("WRITE_EXTERNAL_STORAGE: отклонено") }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    requestPostNotification.launch()
                },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "Environment.getExternalStorage"
                )
            }
            Text(
                fontSize = defaultButtonTextSp,
                text = state.loadingStatePublicDirectory
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    intent(
                        RetrofitDownloadingViewModel.Intent.DownLoadPictureToPublicDirectory(
                            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTBkJigufyq00dk5hZq_acK0ix6Gq5LMj59Kg&s",
                            MyFilesFolders.fileBaseFolderForPublicDownloading,
                            "MyPicture.png"
                        )
                    )
                },
                colors = ButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White,
                ),
            ) {
                Text(
                    fontSize = defaultButtonTextSp,
                    text = "downLoadPicture"
                )
            }
            Text(
                fontSize = defaultButtonTextSp,
                text = state.loadingStatePicturePublicDirectory
            )
        }

    }
}
