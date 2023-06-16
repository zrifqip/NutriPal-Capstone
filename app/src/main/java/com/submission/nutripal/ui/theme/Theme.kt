package com.submission.nutripal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Green200,
    primaryVariant = Green500,
    secondary = Green700
)

private val LightColorPalette = lightColors(
    primary = Green900,
    surface = Green200,
    secondary = Green700,
    background = Green500,
    onSurface = Green700,
    onPrimary = white,
    onSecondary = white,
)

@Composable
fun NutripalTheme( content: @Composable () -> Unit) {
    val colors = LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}