package com.example.domain

import java.util.*

data class WeatherWeeklyForecastModel(
    val cityName: String,
    val forecasts: List<ForecastModel>
)

data class ForecastModel(
    val temperature : Float,
    val date: Date
)
