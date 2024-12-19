package com.golyv.weatherforecast.navigation

enum class Screen{
    FORECAST,
    CAMERA
}

sealed class NavigationItem(val route: String) {
    data object Forecast: NavigationItem(Screen.FORECAST.name)
    data object Camera: NavigationItem(Screen.CAMERA.name)
}

