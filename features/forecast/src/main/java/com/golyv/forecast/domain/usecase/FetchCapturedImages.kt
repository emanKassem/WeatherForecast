package com.golyv.forecast.domain.usecase

import com.golyv.forecast.domain.repository.WeatherForecastRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchCapturedImages @Inject constructor(private val weatherForecastRepo: WeatherForecastRepo) {

    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        weatherForecastRepo.fetchCapturedImages()
    }
}
