package com.golyv.forecast.presentation.model

import com.golyv.forecast.domain.model.WeatherForecastModel

sealed class ForecastUiState {
    data object Loading: ForecastUiState()
    data class Success(val weatherForecastModel: WeatherForecastModel): ForecastUiState()
    data class Failure(val message: String): ForecastUiState()
}