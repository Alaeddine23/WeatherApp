package com.octo.weatherapp

import com.example.data.NetworkModule
import com.example.domain.ForecastInteractor
import com.example.presentation.WeatherWeeklyForecastPresenter
import com.example.presentation.WeatherWeeklyForecastView

class ForecastModule(
    private val view: WeatherWeeklyForecastView,
    private val networkModule: NetworkModule
) {

    fun provideController() = WeatherWeeklyController(provideInteractor())

    fun provideInteractor() = ForecastInteractor(networkModule.provideWeatherNetworkRepository(), providePresenter())

    fun providePresenter() = WeatherWeeklyForecastPresenter(view)

}