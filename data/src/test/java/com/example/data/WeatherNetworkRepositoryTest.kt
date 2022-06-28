package com.example.data

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

class WeatherNetworkRepositoryTest {
    private lateinit var weatherNetworkRepository: WeatherNetworkRepository
    private lateinit var networkModule: NetworkModule
    private lateinit var mockServer: MockWebServer
    private val cityName = "Paris"
    private val unit = "metric"
    private val apiKey = "fakeApiKey"

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        networkModule = NetworkModule()
        val retrofit = networkModule.provideRetrofit(mockServer.url("/").toString())
        weatherNetworkRepository = WeatherNetworkRepository(retrofit)
    }

    @Test
    fun testHappyPath() = runBlocking {
        //given
        val response = MockResponse().setResponseCode(200)
            .setBody(FileUtils.readJson("forecast.json"))
        mockServer.enqueue(response)
        //when
        val forecasts = weatherNetworkRepository.loadCityForecasts(cityName, apiKey, unit)
        //then
        val city = JsonCity(cityName = "Paris")
        val jsonMain = JsonMain(temp = 14.76F)
        val date = "2021-10-01 18:00:00"
        val expectedBody = JsonCityWeeklyForecast(jsonCity = city, listOf(element = JsonForecast(jsonMain, date)))
        assertEquals(expectedBody, forecasts?.body())
    }

    @Test
    fun testSaddyPath() = runBlocking {
        // given
        val response = MockResponse().setResponseCode(404)
        mockServer.enqueue(response)
        //when
        val forecasts: Response<JsonCityWeeklyForecast>? = weatherNetworkRepository.loadCityForecasts(cityName, apiKey, unit)
        //then
        assertEquals(null, forecasts)
    }

    @Test
    fun testEmptyJson() = runBlocking {
        //given
        val response = MockResponse().setResponseCode(200)
            .setBody(FileUtils.readJson("forecast_empty.json"))
        mockServer.enqueue(response)
        //when
        val forecasts = weatherNetworkRepository.loadCityForecasts(cityName, apiKey, unit)
        //then
        val expectedBody = JsonCityWeeklyForecast(null, null)
        assertEquals(expectedBody, forecasts?.body())
    }
}