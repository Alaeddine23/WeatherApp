package com.example.domain

interface WeatherNetworkPresenterInterface {
    fun presentOnSuccess(model: WeatherWeeklyForecastModel)
    fun presentOnFailure()
}