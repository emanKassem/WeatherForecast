package com.golyv.uicomponents.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Blue200 = Color(0xff7FDFF4)
val Red500 = Color(0xffe60000)
val Red700 = Color(0xffcd0000)
val Red800 = Color(0xffd00036)
val BlueLight = Color(0xff0396A6)
val BlueDark = Color(0xff0B698B)
val Gray400 = Color(0xFFCCCCCC)
val Gray600 = Color(0xFF666666)
val Gray900 = Color(0xFF333333)
val Turquoise = Color(0xff007C92)
val NavyBlue = Color(0xff132F5B)
val MidnightBlue = Color(0xFF1D2229)
val Black = Color.Black
val White = Color.White
val DarkGrey = Color(0xFF333333)
val LightGrey = Color(0xFFAFAFAF)
val DarkSlateGray = Color(0xFF2D343B)
val FreshOrange = Color(0xFFEB9700)
val Red = Color(0xFFE60000)

/**
 * Return the fully opaque color that results from compositing onSurface atop surface with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun ColorScheme.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}
