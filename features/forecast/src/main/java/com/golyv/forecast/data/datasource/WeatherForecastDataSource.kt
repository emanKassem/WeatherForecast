package com.golyv.forecast.data.datasource

import com.golyv.database.CapturedImageEntity
import com.golyv.forecast.data.model.WeatherForecastResponseModel
import com.golyv.network.model.ResultState

interface WeatherForecastDataSource {

    suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        exclude: String
    ): ResultState<WeatherForecastResponseModel>

    suspend fun saveImageUri(imageUri: String)
    suspend fun fetchCapturedImages(): List<CapturedImageEntity>
}