package com.example.presentation

import com.example.domain.ForecastModel
import com.example.domain.WeatherWeeklyForecastModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.times
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class WeatherWeeklyForecastPresenterTest{

    @Mock
    private lateinit var weatherWeeklyForecastView: WeatherWeeklyForecastView

    @InjectMocks
    private lateinit var weeklyForecastPresenter: WeatherWeeklyForecastPresenter

    @Test
    fun presentSuccess(){
        //given
        val model = WeatherWeeklyForecastModel("Paris",
        listOf(
            ForecastModel(12F, Date()),
            ForecastModel(16F, Date())
        ))

        val expectedDisplayModel = WeatherWeeklyForecastDisplayModel(
            cityName = "Paris",
            temperatureMin = "12.0°C",
            temperatureMax = "16.0°C"
        )

        //when
        weeklyForecastPresenter.presentOnSuccess(model)

        //then
        then(weatherWeeklyForecastView).should(times(1)).displayForecasts(expectedDisplayModel)
    }

    @Test
    fun presentError(){
        //when
        weeklyForecastPresenter.presentOnFailure()

        //then
        then(weatherWeeklyForecastView).should(times(1)).displayError()
    }
}