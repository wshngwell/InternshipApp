package com.example.internshipapp.presentation.feature2

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.internshipapp.domain.entities.PostEntity
import com.example.internshipapp.myLog
import kotlin.math.roundToInt

@Composable
fun SwipeablePost(
    postEntity: PostEntity,
    onPostClicked: () -> Unit,
    onFavouriteClicked: () -> Unit
) {

    var offsetX by remember { mutableStateOf(0f) }

    val swipeThreshold = 100.dp.toPx()
    Box(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures (
                    onHorizontalDrag = {_, dragAmount ->
                        val newOffset = (offsetX + dragAmount)
                            .coerceAtLeast(0f)
                        offsetX = newOffset
                    },
                    onDragEnd = {
                        if (offsetX >= swipeThreshold) {
                            myLog("SWIPE")
                            onFavouriteClicked()
                            offsetX = 0f
                        }
                    }
                )

            }
    ) {
        Post(
            postEntity = postEntity,
            onFavouriteClicked = {
                onFavouriteClicked()
            },
            onPostClicked = {
                onPostClicked()
            })
    }
}