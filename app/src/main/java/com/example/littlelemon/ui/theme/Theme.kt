package com.example.littlelemon.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColorPalette = lightColors(
    primaryVariant =  LittleLemonColor.yellow,
    primary = LittleLemonColor.yellow,
    secondary = LittleLemonColor.pink,
    secondaryVariant =  LittleLemonColor.pink,
)

@Composable
fun LittleLemonTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}
