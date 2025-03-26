package com.example.internshipapp.ui.theme

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.internshipapp.currentTheme
import com.example.internshipapp.getCurrentTheme
import com.example.internshipapp.myLog

@SuppressLint("RememberReturnType")
@Composable
fun InternshipAppTheme(
    content: @Composable () -> Unit
) {

    val context = LocalContext.current

    val isDarkTheme = isSystemInDarkTheme()

    remember { getCurrentTheme(context, isDarkTheme) }

    val colorScheme by remember(currentTheme.collectAsStateWithLifecycle().value.isSystemDark) {
        mutableStateOf(
            darkColorScheme(
                primary = postColor,
                secondary = secondary,
                tertiary = tertiary,
                background = myBackground,
                onBackground = onBackground,
                onPrimary = buttonsColor,
                onSecondary = disabledButtonsColor
            )
        )
    }

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