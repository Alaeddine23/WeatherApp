package com.example.presentation

interface WeatherWeeklyForecastView {
    fun displayForecasts(displayModel: WeatherWeeklyForecastDisplayModel)
    fun displayError()
}