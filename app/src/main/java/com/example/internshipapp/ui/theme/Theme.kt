package com.example.internshipapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    secondary = secondary,
    tertiary = tertiary,
    background = background,
    onBackground = onBackground,
    onPrimary = buttonsColor,
    onSecondary = disabledButtonsColor
)

@Composable
fun InternshipAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Green.toArgb()// цвет АппБара
            window.navigationBarColor = Color.Red.toArgb() // цвет контрль панели
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                false // светлый или темный цвет для АппБара
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                false // для контроль панели
        }
    }
}