package com.example.internshipapp.presentation.feature2

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.internshipapp.R

@Composable
@Preview
fun CustomProgressBar(
    rotationAngle: Float = 360f,
) {
    val transition = rememberInfiniteTransition(label = "")
    val rotation = transition.animateFloat(
        initialValue = 0f,
        targetValue = rotationAngle,
        animationSpec = infiniteRepeatable(
            tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Rotation"
    )
    Image(
        painter = painterResource(id = R.drawable.img),
        contentDescription = null,
        modifier = Modifier
            .size(50.dp)
            .rotate(rotation.value)
    )
}
