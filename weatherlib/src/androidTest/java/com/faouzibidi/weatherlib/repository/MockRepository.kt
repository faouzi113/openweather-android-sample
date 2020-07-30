package com.faouzibidi.weatherlib.repository

import WeatherResult
import android.content.Context
import com.faouzibidi.weatherlib.model.Exclude
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockRepository(context : Context) {

    companion object {
        val BASE_URL = "https://localhost"
        val PORT = 8000
        val API_URL = "/data/2.5/onecall"
    }

    // the weatherApi retrofit interface
    var weatherApi: WeatherApi

    init {
        // initializing the wetaherApi instance
        val retrofit = Retrofit.Builder()
            .baseUrl(MockRepository.BASE_URL+":"+PORT+"/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherInfo(lat: String, lon: String, exclude: Exclude, appId: String): WeatherResult? {
        return weatherApi.getWeather(lat, lon, exclude.name, appId).execute().body()
    }
}