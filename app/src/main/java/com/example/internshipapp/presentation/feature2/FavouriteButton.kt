package com.example.internshipapp.presentation.feature2

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun FavoriteButton(isFavorite: Boolean = true, onClick: () -> Unit = {}) {

    val colorAnimation = rememberInfiniteTransition(label = "")
    val color by colorAnimation.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Magenta,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val sizeAnimation = rememberInfiniteTransition(label = "")
    val size by sizeAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val icon = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    Box(
        modifier = Modifier.size(50.dp)

    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Favorite",
            tint = if (isFavorite) color else Color.Gray,
            modifier = Modifier
                .size(if (isFavorite) (48.dp * size) else 48.dp)
                .clickable { onClick() }
        )
    }

}