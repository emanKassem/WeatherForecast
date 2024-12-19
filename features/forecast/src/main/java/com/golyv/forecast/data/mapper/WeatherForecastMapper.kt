package com.golyv.forecast.data.mapper

import com.golyv.forecast.data.model.WeatherForecastResponseModel
import com.golyv.forecast.domain.model.CurrentWeatherForecast
import com.golyv.forecast.domain.model.HourlyForecast
import com.golyv.forecast.domain.model.UV
import com.golyv.forecast.domain.model.WeatherForecastModel
import com.golyv.network.BuildConfig
import com.golyv.utils.convertUnixToTime

fun WeatherForecastResponseModel.toDomainModel() = WeatherForecastModel(
    timezone = timezone,
    current = CurrentWeatherForecast(
        currentStatus = current.weather[0].main,
        description = current.weather[0].description,
        icon = BuildConfig.BASE_IMG_URL + current.weather[0].icon + "@2x.png",
        temperature = current.temp.toInt().toString()+"°",
        humidity = current.humidity,
        visibility = current.visibility / 1000,
        windSpeed = current.windSpeed.toInt(),
        uvi = current.uvi.toUV(),
        airPressure = current.pressure
    ),
    hourly = hourly.map { hourlyForecast ->
        HourlyForecast(
            time = hourlyForecast.dt.convertUnixToTime(),
            icon = BuildConfig.BASE_IMG_URL + hourlyForecast.weather[0].icon + "@2x.png",
            temperature = hourlyForecast.temp.toInt().toString()+"°"
        )
    }
)

fun Double.toUV(): UV =
    when (this) {
        in 0.0..3.0 -> UV.LOW
        in 3.0..7.0 -> UV.MODERATE
        else -> UV.HIGH
    }
