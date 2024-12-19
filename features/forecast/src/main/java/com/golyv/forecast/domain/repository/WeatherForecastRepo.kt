package com.golyv.forecast.domain.repository

import com.golyv.forecast.domain.model.WeatherForecastModel
import com.golyv.network.model.ResultState

interface WeatherForecastRepo {

    suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        exclude: String
    ): ResultState<WeatherForecastModel>

    suspend fun saveImageUri(imageUri: String)
    suspend fun fetchCapturedImages(): List<String>
}