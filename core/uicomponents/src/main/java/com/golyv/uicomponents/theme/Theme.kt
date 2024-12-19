package com.golyv.uicomponents.theme

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val lightThemeColors = lightColorScheme(
    primary = Blue200,
    onPrimary = White,
    primaryContainer = NavyBlue,
    onPrimaryContainer = White,
    inversePrimary = Gray600,
    secondary = Red500,
    onSecondary = Gray400,
    secondaryContainer = Red,
    onSecondaryContainer = White,
    tertiary = Turquoise,
    onTertiary = White,
    background = BlueLight,
    onBackground = White,
    surface = White,
    onSurface = Gray900,
    surfaceVariant = White,
    inverseSurface = BlueDark,
    surfaceTint = White,
    error = Red800,
    outline = Gray900
)

@SuppressLint("ConflictingOnColor")
private val darkThemeColors = darkColorScheme(
    primary = Blue200,
    onPrimary = Color.Black,
    onPrimaryContainer = White,
    secondary = Blue200,
    onSecondary = Color.Black,
    secondaryContainer = Red,
    background = Gray900,
    onBackground = BlueLight,
    surface = Gray900,
    onSurface = BlueLight,
    surfaceTint = Color.Black,
    error = Blue200,
    inverseSurface = DarkSlateGray,
    inverseOnSurface = White,
    primaryContainer = MidnightBlue,
    inversePrimary = LightGrey,
    surfaceVariant = White,
    onSurfaceVariant = Black,
    onSecondaryContainer = White,
    tertiary = FreshOrange,
    onTertiary = White,
    outline = Black,
    outlineVariant = White,
    tertiaryContainer = DarkGrey,
    onTertiaryContainer = White,
)

private val lightElevations = Elevations(card = 0.dp)
private val darkElevations = Elevations(card = 1.dp)

private val lightImages = Images(logo = 0)
private val darkImages = Images(logo = 0)

@Composable
fun AppTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkThemeColors else lightThemeColors
    val elevations = if (darkTheme) darkElevations else lightElevations
    val images = if (darkTheme) darkImages else lightImages
    CompositionLocalProvider(
        LocalElevations provides elevations,
        LocalImages provides images
    ) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}