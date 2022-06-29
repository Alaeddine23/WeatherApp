package com.example.domain

import java.util.*

data class WeatherWeeklyForecastModel(
    val cityName: String,
    val forecasts: List<ForecastModel>
) {
    val minForecast: ForecastModel
        by lazy { forecasts.minBy { it.temperature } }

    val maxForecast: ForecastModel
        by lazy { forecasts.maxBy { it.temperature } }
}

data class ForecastModel(
    val temperature: Float,
    val date: Date
)
