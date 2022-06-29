package com.example.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun provideRetrofit(url: String = BASE_URL): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideWeatherNetworkRepository(): WeatherNetworkRepository =
        WeatherNetworkRepository(provideRetrofit())
}