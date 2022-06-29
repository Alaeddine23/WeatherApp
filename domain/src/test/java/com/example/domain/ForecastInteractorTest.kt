package com.example.domain

import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
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
        runBlocking {
            //given
            given(weatherNetworkRepositoryInterface.loadCityForecast(name))
                .willReturn(Failure)
            //when
            interactor.loadForecast(cityName = name)
        }
        //then
        then(presenter).should(times(1)).presentOnFailure()
    }

    @Test
    fun loadForecastWhenRepositoryReturnSuccessAndModelContainsTwoForecastsShouldPresentSuccess() {
        //given
        val model = generateModelWithForecasts()
        runBlocking {
            given(weatherNetworkRepositoryInterface.loadCityForecast(name))
                .willReturn(Success(model = model))
            //when
            interactor.loadForecast(cityName = name)
            //then
            then(presenter).should(times(1)).presentOnSuccess(model = model)

        }
           }

    @Test
    fun loadForecastWhenRepositoryReturnSuccessAndModelContainsOneForecastShouldPresentFailure() {
        runBlocking {
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
        }
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