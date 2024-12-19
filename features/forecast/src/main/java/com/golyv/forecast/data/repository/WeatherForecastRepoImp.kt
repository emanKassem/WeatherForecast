package com.golyv.forecast.data.repository

import com.golyv.forecast.data.datasource.WeatherForecastDataSource
import com.golyv.forecast.data.mapper.toDomainModel
import com.golyv.forecast.domain.model.WeatherForecastModel
import com.golyv.forecast.domain.repository.WeatherForecastRepo
import com.golyv.network.mapper.map
import com.golyv.network.model.ResultState

class WeatherForecastRepoImp(
    private val weatherForecastDataSource: WeatherForecastDataSource
) : WeatherForecastRepo {
    override suspend fun getWeatherForecast(
        latitude: String,
        longitude: String,
        exclude: String
    ): ResultState<WeatherForecastModel> =
        weatherForecastDataSource.getWeatherForecast(latitude, longitude, exclude).map {
            it.toDomainModel()
        }

    override suspend fun saveImageUri(imageUri: String) {
        weatherForecastDataSource.saveImageUri(imageUri)
    }

    override suspend fun fetchCapturedImages() =
        weatherForecastDataSource.fetchCapturedImages().map { it.uri }

}