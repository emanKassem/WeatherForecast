package com.golyv.forecast.domain.usecase

import com.golyv.forecast.domain.repository.WeatherForecastRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveImageUri @Inject constructor(val weatherForecastRepo: WeatherForecastRepo) {

    suspend operator fun invoke(imageUri: String) = withContext(Dispatchers.IO) {
        weatherForecastRepo.saveImageUri(imageUri)
    }
}
