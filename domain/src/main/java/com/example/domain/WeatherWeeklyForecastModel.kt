package com.example.domain

data class WeatherWeeklyForecastModel(
    val cityName: String,
    val forecasts: List<ForecastModel>
)

data class ForecastModel(
    val temperature : Float,
    val date: String
)
