package com.example.internshipapp.presentation.feature12

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import com.example.internshipapp.R
import com.example.internshipapp.myLog
import com.example.internshipapp.presentation.feature12.MediaPlayerViewModel.Intent
import com.example.internshipapp.presentation.feature12.MediaPlayerViewModel.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import org.koin.androidx.compose.koinViewModel
import java.util.concurrent.TimeUnit

@RootNavGraph
@Destination
@Composable
fun Media3PlayerView() {
    val viewModel = koinViewModel<MediaPlayerViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (Intent) -> Unit by remember {
        mutableStateOf(viewModel::sendIntent)
    }

    var playerView: PlayerView? by remember {
        mutableStateOf(null)
    }
    DisposableEffect(Unit) {
        onDispose {
            playerView?.player = null
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable {
                intent(
                    Intent.UpdateShouldControlsButtonsBeVisible(
                        !state.shouldControlsButtonsBeVisible
                    )
                )
            },
    ) {
        AndroidView(
            factory = {
                val view = PlayerView(it).apply {
                    useController = false
                    player = state.player

                }
                playerView = view
                view

            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .align(Alignment.Center)

        )
        if (state.shouldControlsButtonsBeVisible) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp),
                onClick = {
                    intent(Intent.IsPlayingChange)
                    if (state.isPlaying) intent(Intent.Pause) else intent(Intent.Play)
                }
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = when {
                        state.buffered -> {
                            painterResource(id = R.drawable.img)
                        }

                        state.isPlaying -> {
                            painterResource(id = R.drawable.pause)
                        }

                        else -> {
                            painterResource(id = R.drawable.play)
                        }
                    },
                    contentDescription = "Play/Pause"
                )
            }
            BottomControls(
                modifier = Modifier.align(Alignment.BottomCenter),
                state = state,
                intent = intent
            )
        }
    }
}

@Composable
private fun BottomControls(
    modifier: Modifier = Modifier,
    state: State,
    intent: (Intent) -> Unit = {}
) {

    Column(modifier = modifier.padding(bottom = 32.dp)) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Slider(
                modifier = Modifier.fillMaxWidth(),
                value = state.currentPositionOfSlider.toFloat(),
                onValueChange = {
                    intent(Intent.ChangeIsSliderDragging(true))
                    intent(Intent.UpdateCurrentPositionOfSlider(it.toLong()))
                },
                onValueChangeFinished = {
                    intent(Intent.ChangeIsSliderDragging(false))
                },
                valueRange = 0f..state.videoDuration.toFloat(),
                colors =
                SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTickColor = Color.Red
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = (
                        state.currentPositionOfSlider.formatMinSec() + "/" + state.videoDuration.formatMinSec()
                        ),
                color = Color.Cyan
            )
        }
    }
}

@SuppressLint("DefaultLocale")
fun Long.formatMinSec(): String {

    return String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(this),
        TimeUnit.MILLISECONDS.toSeconds(this) -
                TimeUnit.MINUTES.toSeconds(
                    TimeUnit.MILLISECONDS.toMinutes(this)
                )
    )
}

