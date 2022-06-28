package com.example.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class NetworkModule {

    companion object {
        private const val BASE_URL = "https://pro.openweathermap.org/data/2.5/"
    }

    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideWeatherService(): WeatherService{
        return provideRetrofit().create(WeatherService::class.java)
    }
}