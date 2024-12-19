package com.golyv.uicomponents.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.golyv.uicomponents.R

internal val DaltonMaagVodafone = FontFamily(
    Font(R.font.sf_regular, FontWeight.Normal),
    Font(R.font.sf_semibold, FontWeight.Medium),
    Font(R.font.sf_bold, FontWeight.Bold)
)

val Typography = Typography(
    displaySmall = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    displayMedium = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    displayLarge = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    titleSmall = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 18.37.sp
    ),
    titleMedium = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    bodySmall = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontSize = 16.sp,
        fontWeight = FontWeight.Light,
        lineHeight = 16.sp,
        letterSpacing = 0.15.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 16.sp,
        letterSpacing = 0.15.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 18.sp,
        letterSpacing = 0.15.sp
    ),
    labelSmall = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = DaltonMaagVodafone,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )
)