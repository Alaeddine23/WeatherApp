package com.octo.weatherapp

import com.example.data.NetworkModule
import com.example.domain.ForecastInteractor
import com.example.presentation.WeatherWeeklyForecastPresenter
import com.example.presentation.WeatherWeeklyForecastView

object ForecastModule {
    fun provideInteractor() = ForecastInteractor(NetworkModule.provideWeatherNetworkRepository())
}