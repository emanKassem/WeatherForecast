package com.golyv.weatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.golyv.uicomponents.theme.AppTheme
import com.golyv.weatherforecast.navigation.ForecastNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherForecastActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            AppTheme {
                ForecastNavHost()
            }
        }
    }
}