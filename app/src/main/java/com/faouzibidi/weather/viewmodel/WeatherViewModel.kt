package com.faouzibidi.weather.viewmodel

import WeatherResult
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faouzibidi.weatherlib.interactor.WeatherInteractor
import com.faouzibidi.weatherlib.model.Exclude
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val weatherInteractor : WeatherInteractor = WeatherInteractor(application)
    private var weatherLiveData = MutableLiveData<WeatherResult>()

    // Make a network request without blocking the UI thread
    private fun getWeather(lat : String, lon : String, exclude : Exclude, appId : String) {
        // launch a coroutine in viewModelScope
        viewModelScope.launch  {
            val result = weatherInteractor.getWeatherInfo(lat,lon,exclude,appId)
            // dispatch results
            weatherLiveData.value = result
        }
    }
}