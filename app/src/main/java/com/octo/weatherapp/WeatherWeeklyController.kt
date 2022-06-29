package com.octo.weatherapp

import com.example.domain.ForecastInteractor

class WeatherWeeklyController(
    private val interactor: ForecastInteractor,
    ) {

    suspend fun loadForecast(cityName: String) {
        interactor.loadForecast(cityName)
    }

}