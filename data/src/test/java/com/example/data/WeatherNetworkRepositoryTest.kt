package com.example.data

import com.example.domain.ForecastModel
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Failure
import com.example.domain.WeatherNetworkRepositoryInterface.WeatherWeeklyForecastResponse.Success
import com.example.domain.WeatherWeeklyForecastModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.util.*

@ExtendWith(MockitoExtension::class)
class WeatherNetworkRepositoryTest {
    private lateinit var weatherNetworkRepository: WeatherNetworkRepository
    private lateinit var networkModule: NetworkModule
    private lateinit var mockServer: MockWebServer
    private val cityName = "Paris"

    @BeforeEach
    fun setUp() {
        mockServer = MockWebServer()
        networkModule = NetworkModule()
        val retrofit = networkModule.provideRetrofit(mockServer.url("/").toString())
        weatherNetworkRepository = WeatherNetworkRepository(retrofit)
    }

    @Test
    fun testHappyPath() {
        //given
        val response = MockResponse().setResponseCode(200)
            .setBody(FileUtils.readJson("forecast.json"))
        mockServer.enqueue(response)
        //when
        val forecasts = runBlocking { weatherNetworkRepository.loadCityForecast(cityName) }
        //then
        val date = Date.from(
            LocalDateTime.of(2021, Month.OCTOBER, 1, 18, 0, 0).toInstant(
                ZoneId.systemDefault().rules.getOffset(
                    Instant.now()
                )
            )
        )
        val expected = Success(
            model = WeatherWeeklyForecastModel(
                cityName = "Paris",
                listOf(element = ForecastModel(temperature = 14.76F, date = date))
            )
        )
        assertThat(forecasts).isEqualTo(expected)
    }

    @Test
    fun testSaddyPath() {
        // given
        val response = MockResponse().setResponseCode(404)
        mockServer.enqueue(response)
        //when
        val forecasts = runBlocking { weatherNetworkRepository.loadCityForecast(cityName) }
        //then
        assertThat(forecasts).isEqualTo(Failure)
    }

    @Test
    fun testEmptyJson() {
        //given
        val response = MockResponse().setResponseCode(200)
            .setBody(FileUtils.readJson("forecast_empty.json"))
        mockServer.enqueue(response)
        //when
        val forecasts = runBlocking { weatherNetworkRepository.loadCityForecast(cityName) }
        //then
        assertThat(forecasts).isEqualTo(
            Success(
                WeatherWeeklyForecastModel(
                    cityName = "",
                    forecasts = emptyList()
                )
            )
        )
    }
}