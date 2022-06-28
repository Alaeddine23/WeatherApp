package com.example.domain

import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.BDDMockito.times
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class ForecastInteractorTest {

    @Mock
    lateinit var weatherNetworkRepositoryInterface: WeatherNetworkRepositoryInterface

    @Mock
    lateinit var presenter: WeatherNetworkPresenterInterface
    @InjectMocks
    lateinit var interactor: ForecastInteractor

    private val name = "Paris"

    @Test
    fun loadForecastWhenRepositoryReturnFailureShouldPresentFailure() {
        //given
        given(weatherNetworkRepositoryInterface.loadCityForecast(name))
            .willReturn(Failure)
        //when
        interactor.loadForecast(cityName = name)
        //then
        then(presenter).should(times(1)).presentOnFailure()
    }

    @Test
    fun loadForecastWhenRepositoryReturnSuccessAndModelContainsTwoForecastsShouldPresentSuccess() {
        //given
        val model = generateModelWithForecasts()
        given(weatherNetworkRepositoryInterface.loadCityForecast(name))
            .willReturn(Success(model = model))
        //when
        interactor.loadForecast(cityName = name)
        //then
        then(presenter).should(times(1)).presentOnSuccess(model = model)
    }

    @Test
    fun loadForecastWhenRepositoryReturnSuccessAndModelContainsOneForecastShouldPresentFailure() {
        //given
        val model = WeatherWeeklyForecastModel(
            cityName = name,
            forecasts = listOf(
                ForecastModel(
                    temperature = 25f,
                    date = Date()
                )
            )
        )
        given(weatherNetworkRepositoryInterface.loadCityForecast(name))
            .willReturn(Success(model = model))
        //when
        interactor.loadForecast(cityName = name)
        //then
        then(presenter).should(times(1)).presentOnFailure()
    }

    private fun generateModelWithForecasts(): WeatherWeeklyForecastModel =
        WeatherWeeklyForecastModel(
            cityName = name,
            forecasts = listOf(
                ForecastModel(
                    temperature = 25f,
                    date = Date()
                ),
                ForecastModel(
                    temperature = 25f,
                    date = Date()
                )
            )
        )
}