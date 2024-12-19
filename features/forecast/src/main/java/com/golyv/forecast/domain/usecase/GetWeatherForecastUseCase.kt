package com.golyv.forecast.domain.usecase

import com.golyv.forecast.domain.repository.WeatherForecastRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(
    private val weatherForecastRepo: WeatherForecastRepo
) {

    suspend operator fun invoke(
        latitude: String,
        longitude: String,
        exclude: String
    ) = withContext(Dispatchers.IO) {
        weatherForecastRepo.getWeatherForecast(latitude, longitude, exclude)
    }
}