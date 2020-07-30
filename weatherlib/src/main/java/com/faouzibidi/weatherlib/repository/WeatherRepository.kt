package com.faouzibidi.weatherlib.repository

import WeatherResult
import android.content.Context
import com.faouzibidi.weatherlib.helpers.ConnectivityHelper
import com.faouzibidi.weatherlib.model.Exclude
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 *
 * Class used for retrieving and storing weather data
 *
 * This class is the global and the abstract repository used for getting data either from the
 * openWeather API server or from the local file if there is no internet connection
 *
 * In the first time we check if the device is connected to any network, if so, we create
 * an instance of @RemoteRepository which calls in real time openWeather API server and stores
 * data in a local file.
 * If there is no network available we get stored data from the local file
 *
 * @author faouzi BIDI
 *
 */

abstract class WeatherRepository private constructor(context:Context){

    protected val context = context

    abstract fun  getWeatherInfo(lat : String,lon : String, exclude : Exclude, appId : String) : WeatherResult?

    companion object {
        @Volatile private var INSTANCE : WeatherRepository? = null
        
        fun getInstance(context: Context): WeatherRepository {
            val i = INSTANCE
            if (i != null && i is RemoteRepository) {
                return i
            }

            if (ConnectivityHelper.isConnected(context)){
                INSTANCE = RemoteRepository(context)
            }else{
                INSTANCE = LocalRepository(context)
            }

            return INSTANCE as WeatherRepository
        }
    }
    
    /**
     * this class is used as a remote repository so it brings data in real time
     * from the openWeather API
     *
     * this repository should be used when there is an active internet connection
     */
    private class RemoteRepository(context : Context): WeatherRepository(context) {
        // the weatherApi retrofit interface
        var weatherApi : WeatherApi

        init {
            // initializing the wetaherApi instance
            val gson = GsonBuilder().create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            weatherApi = retrofit.create(WeatherApi::class.java)
        }

        override fun getWeatherInfo(lat : String,lon : String, exclude : Exclude, appId : String): WeatherResult? {
            val result =  weatherApi.getWeather(lat, lon, exclude.name, appId).execute().body()
            /*
                every time we get non-nullable data we store them into the local repository
             */
            if (result != null) {
                LocalRepository(context).storeData(result)
            }
            return result
        }

    }

    /**
     * this class is used as a local store for weather informations, it stores and retrieves data
     * from a local file
     *
     * this repository is used only if there is no internet connection
     */
    private class LocalRepository(context : Context): WeatherRepository(context){

        private val WEATHER_KEY = "weather"
        private val WEATHER_FILE_NAME = "weather_file"


        override fun getWeatherInfo(lat : String,lon : String, exclude : Exclude, appId : String): WeatherResult? {
            val weatherStr = readData()
            if(weatherStr != null){
                return Gson().fromJson(weatherStr, WeatherResult::class.java)
            }
            return null
        }

        /**
         * read data from the shared preferences local file
         */
        fun readData() : String? {
            return context.getSharedPreferences(WEATHER_FILE_NAME, Context.MODE_PRIVATE)
                .getString("weather", null)
        }

        /**
         * store data into the shared preferences local file
         */
        fun storeData(weatherResult: WeatherResult){
            context.getSharedPreferences(WEATHER_FILE_NAME, Context.MODE_PRIVATE)
                .edit()
                .putString(WEATHER_KEY, weatherResult.toJsonString())
                .apply()
        }



    }

}