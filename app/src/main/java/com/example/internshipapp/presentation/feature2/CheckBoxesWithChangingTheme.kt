package com.example.internshipapp.presentation.feature2

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.internshipapp.ThemeType
import com.example.internshipapp.saveCurrentTheme
import com.example.internshipapp.ui.theme.radioButtonColor


@Preview
@Composable
fun SwitchThemeCheckBoxes() {
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    var lightThemeState by remember {
        mutableStateOf(false)
    }
    var darkThemeState by remember {
        mutableStateOf(false)
    }
    var systemThemeState by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 80.dp)
    ) {
        RadioButton(
            selected = lightThemeState,
            onClick = {
                lightThemeState = true
                darkThemeState = false
                systemThemeState = false
                saveCurrentTheme(
                    context = context,
                    themeType = ThemeType.LIGHT,
                    isDarkTheme = isDarkTheme
                )
            },
            colors = RadioButtonColors(
                selectedColor = radioButtonColor,
                unselectedColor = Color.Gray,
                disabledSelectedColor = Color.Gray,
                disabledUnselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
        RadioButton(
            selected = darkThemeState,
            onClick = {
                darkThemeState = true
                lightThemeState = false
                systemThemeState = false
                saveCurrentTheme(
                    context = context,
                    themeType = ThemeType.DARK,
                    isDarkTheme = isDarkTheme
                )
            },
            colors = RadioButtonColors(
                selectedColor = radioButtonColor,
                unselectedColor = Color.Gray,
                disabledSelectedColor = Color.Gray,
                disabledUnselectedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
        RadioButton(
            selected = systemThemeState,
            onClick = {
                systemThemeState = true
                lightThemeState = false
                darkThemeState = false
                saveCurrentTheme(
                    context = context,
                    themeType = ThemeType.SYSTEM,
                    isDarkTheme = isDarkTheme
                )
            },
            colors = RadioButtonColors(
                selectedColor = radioButtonColor,
                unselectedColor = Color.Gray,
                disabledSelectedColor = Color.Gray,
                disabledUnselectedColor = Color.Gray
            )
        )

    }
}