package com.golyv.forecast.data.model

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponseModel(
    val current: NetworkCurrent,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    val daily: List<NetworkDaily>,
    val hourly: List<NetworkHourly>,
)

data class NetworkCurrent(
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Long,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: String,
    val pressure: String,
    val sunrise: Long,
    val sunset: Long,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    val weather: List<NetworkWeather>,
)

data class NetworkWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class NetworkDaily(
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Long,
    @SerializedName("feels_like")
    val feelsLike: NetworkFeelsLike,
    val humidity: Int,
    @SerializedName("moon_phase")
    val moonPhase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pop: Double,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: TempDto,
    val uvi: Double,
    val weather: List<NetworkWeather>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)

data class NetworkFeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)

data class TempDto(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)

data class NetworkHourly(
    val clouds: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val dt: Long,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<NetworkWeather>,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double
)

