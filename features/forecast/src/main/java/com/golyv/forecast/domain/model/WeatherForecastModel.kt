package com.golyv.forecast.domain.model

data class WeatherForecastModel(
    val timezone: String,
    val current: CurrentWeatherForecast,
    val hourly: List<HourlyForecast>

)

data class CurrentWeatherForecast(
    val currentStatus: String,
    val description: String,
    val temperature: String,
    val icon: String,
    val humidity: String,
    val visibility: Int,
    val windSpeed: Int,
    val uvi: UV,
    val airPressure: String

)

data class HourlyForecast(
    val time: String,
    val icon: String,
    val temperature: String
)

enum class UV(val value: String){
    LOW("UV Weak"),
    MODERATE("UV Moderate"),
    HIGH("UV High")
}
