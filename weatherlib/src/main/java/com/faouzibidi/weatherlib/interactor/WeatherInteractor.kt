package com.faouzibidi.weatherlib.interactor

import WeatherResult
import android.content.Context
import com.faouzibidi.weatherlib.model.Exclude
import com.faouzibidi.weatherlib.repository.WeatherRepository

/**
 * this class is used as an interactor as we are usnig an MVVMi architecture
 * this interactor will handle data after calling the repository then it will
 * present them to the viewmodel
 *
 * */
class WeatherInteractor(context: Context) {

    val weatherRepository : WeatherRepository
    init {
        weatherRepository = WeatherRepository.getInstance(context)
    }

    fun getWeatherInfo(lat : String, lon : String, exclude : Exclude, appId : String) : WeatherResult?{
        return weatherRepository.getWeatherInfo(lat, lon, exclude, appId)
    }



}