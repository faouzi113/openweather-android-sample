package com.faouzibidi.weatherlib

import WeatherResult
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.faouzibidi.weatherlib.model.Exclude
import com.faouzibidi.weatherlib.repository.WeatherRepository
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTests{

    /**
     * we try to call this url https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&exclude=daily&appid=f81527ca4ff3e3292b52b6db4232509d
     * and check results
     */
    @Test
    fun simpleTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repository = WeatherRepository.getInstance(appContext)
        //
        val result = repository.getWeatherInfo("33.441792", "-94.037689", Exclude.daily, "f81527ca4ff3e3292b52b6db4232509d")
        Assert.assertNotNull(result)
        //
        checkResults(result!!)
    }

    /**
     * in this method we should check between the returned result from the api
     * and the expected values
     */
    private fun checkResults(weatherResult: WeatherResult){
        Assert.assertEquals(weatherResult.lat, 33.44)
        Assert.assertEquals(weatherResult.lon, -94.04)
        Assert.assertEquals(weatherResult.timezone, "America/Chicago")
        // for the example we just checked 3 values but we can check every values we want more

    }
}