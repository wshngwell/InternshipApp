package com.example.internshipapp.presentation.feature7

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.roundToInt

@RootNavGraph
@Destination
@Composable
fun TwoBoxesTask(
    navigator: DestinationsNavigator
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }


    var blackBoxSize by remember { mutableStateOf(IntSize.Zero) }

    var redBoxSize by remember { mutableStateOf(IntSize.Zero) }

    var initialRedPosition by remember { mutableStateOf(Offset(-1f,-1f)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(250.dp)
                .background(Color.Blue)
                .onGloballyPositioned { coordinates ->
                    blackBoxSize = coordinates.size
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .width(45.dp)
                    .height(60.dp)
                    .background(Color.Red)
                    .onGloballyPositioned { coordinates ->
                        redBoxSize = coordinates.size
                        if (initialRedPosition == Offset(-1f,-1f)) {
                            initialRedPosition = Offset(
                                coordinates.positionInParent().x,
                                coordinates.positionInParent().y
                            )

                        }
                    }
                    .pointerInput(Unit) {

                        detectDragGestures { _, dragAmount ->

                            val newOffsetX = offsetX + dragAmount.x
                            val newOffsetY = offsetY + dragAmount.y

                            val minOffsetX = initialRedPosition.x
                            val minOffsetY = initialRedPosition.y

                            val maxOffsetX =
                                (blackBoxSize.width - redBoxSize.width) - initialRedPosition.x

                            val maxOffsetY =
                                (blackBoxSize.height - redBoxSize.height) - initialRedPosition.y

                            offsetX = newOffsetX.coerceIn(-minOffsetX, maxOffsetX)

                            offsetY = newOffsetY.coerceIn(-minOffsetY, maxOffsetY)
                        }
                    }
            ) { }
        }
    }
}