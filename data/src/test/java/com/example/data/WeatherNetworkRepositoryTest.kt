package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.internal.util.MockUtil
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class WeatherNetworkRepositoryTest{

    private lateinit var weatherNetworkRepository: WeatherNetworkRepository

    @Mock
    private lateinit var networkModule: NetworkModule

    @Mock
    private lateinit var weatherService: WeatherService

    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp(){
        mockServer = MockWebServer()
        weatherService = mock(WeatherService::class.java)
        networkModule = mock(NetworkModule::class.java)
        weatherNetworkRepository = WeatherNetworkRepository(networkModule)
    }

    @Test
    fun testHappyPath() = runBlocking{

        //given
        val cityName = "Paris"
        val unit = "metric"
        val apiKey = "fakeApiKey"

        val response = MockResponse().setResponseCode(200)
            .setBody(JsonResponseExample.jsonString)
        mockServer.enqueue(response)

        //given(networkModule.provideWeatherService().getJsonCityWeeklyForecast(cityName, unit, apiKey))
          //  .willReturn(response)

        //when
        val forecasts: JsonCityWeeklyForecast? = weatherNetworkRepository.loadCityForecasts(cityName,apiKey,unit)

        //then
        assertEquals("Paris", forecasts?.jsonCity?.cityName)
    }

    private fun generateCityWeeklyForecast(cityName: String): JsonCityWeeklyForecast =
        JsonCityWeeklyForecast(jsonCity = JsonCity("Paris"), jsonForecasts = listOf())
}