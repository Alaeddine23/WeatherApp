package com.example.domain

import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.*

class ForecastInteractor(private val repository: WeatherNetworkRepositoryInterface,
                         private val weatherNetworkPresenterInterface: WeatherNetworkPresenterInterface) {
    fun loadForecast(cityName: String) {
        when (val result = repository.loadCityForecast(cityName = cityName)) {
            is Success -> weatherNetworkPresenterInterface.presentOnSuccess(model = result.model)
            Failure -> weatherNetworkPresenterInterface.presentOnFailure()
        }
    }
}