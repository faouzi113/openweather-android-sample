package com.faouzibidi.weatherlib.repository

import WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("/data/2.5/onecall")
    fun getWeather(@Query("lat") latitude : String, @Query("lon") longitude : String, @Query("exclude") exclude : String,@Query("appid") appId : String) : Call<WeatherResult>
}