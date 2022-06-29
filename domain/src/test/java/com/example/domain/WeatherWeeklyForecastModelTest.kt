package com.example.domain

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WeatherWeeklyForecastModelTest{

    @Test
    fun minForecast_ReturnMinForecastInAListOf2(){
        //given
        val expectedMinForecast = ForecastModel(8.2F, Date())
        val model = WeatherWeeklyForecastModel("Paris", listOf(
            ForecastModel(12F, Date()),
            expectedMinForecast,
        ))

        //when
        val minForecast = model.minForecast

        //then
        assertEquals(expectedMinForecast, minForecast)
    }

    @Test
    fun maxForecast_ReturnMaxForecastInAListOf2(){
        //given
        val expectedMaxForecast = ForecastModel(45F, Date())
        val model = WeatherWeeklyForecastModel("Paris", listOf(
            ForecastModel(12F, Date()),
            expectedMaxForecast,
        ))

        //when
        val maxForecast = model.maxForecast

        //then
        assertEquals(expectedMaxForecast, maxForecast)
    }
}