package com.golyv.weatherforecast.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.golyv.camera.ui.CameraScreen
import com.golyv.forecast.presentation.WeatherForecastViewModel
import com.golyv.forecast.presentation.WeatherScreen
import com.golyv.forecast.presentation.model.ForecastUiEvent
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ForecastNavHost(
    modifier: Modifier = Modifier,
    startDestination: String = NavigationItem.Forecast.route
) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val weatherForecastViewModel: WeatherForecastViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Forecast.route) {
            WeatherScreen(
                weatherForecastViewModel = weatherForecastViewModel
            ) {
                navController.navigate(NavigationItem.Camera.route)
            }
        }
        composable(
            route = NavigationItem.Camera.route
        ) {
            CameraScreen { uri ->
                weatherForecastViewModel.onUiEvent(ForecastUiEvent.ImageCaptured(uri))
                if (!navController.popBackStack()) {
                    (context as? Activity)?.finish()
                }
            }
        }
    }
}