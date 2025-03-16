package com.example.internshipapp.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.internshipapp.currentTheme
import com.example.internshipapp.myLog

val primary = Color(0xFFD0BCFF)
val secondary = Color(0xFFCCC2DC)
val tertiary = Color(0xFFEFB8C8)
val onBackground = Color.White
val buttonsColor = Color.Magenta.copy(alpha = 0.5f)
val disabledButtonsColor = Color.Gray
val commentColor = Color(0xFF1A4876)
val myBackground: Color
    get() {
        myLog("mBackground")
        return if (currentTheme.value.isSystemDark) {
            Color.Black

        } else {
            Color.White
        }
    }

val onPostColorContent: Color
    get() {
        return if (currentTheme.value.isSystemDark) {

            Color.Black

        } else {

            Color.White
        }
    }
val postColor: Color
    get() {
        return if (currentTheme.value.isSystemDark) {
            Color.White

        } else {
            Color.Black
        }
    }

val radioButtonColor:Color
    get() {
        return if (currentTheme.value.isSystemDark) {
            Color.White

        } else {
            Color.Black
        }
    }
