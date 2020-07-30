package com.faouzibidi.weatherlib

import WeatherResult
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.faouzibidi.weatherlib.model.Exclude
import com.faouzibidi.weatherlib.repository.MockRepository
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class MockRepositoryInstrumentedTests{
    val mockWebServer: MockWebServer

    init {
        mockWebServer = MockWebServer()
    }


    @Before
    fun startServer(){
        mockWebServer.start(MockRepository.PORT)
        mockWebServer.url("/")
        //
        mockWebServer.dispatcher = MockDispatcher()
    }

    @After
    fun stopServer(){
        mockWebServer.shutdown()
    }


    /**
     * Create a dispatcher for handling server simulated responses
     */
    class MockDispatcher() : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when(request.path){
                MockRepository.API_URL -> prepareResponse()
                else -> MockResponse()
            }
        }

        /**
         * simulate a response from the server using test data
         */
        fun prepareResponse():MockResponse{
            val response = MockResponse()
            // simulate a response with code 200
            response.setResponseCode(200)
            // fil response body with mock data
            val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            response.setBody(appContext.getString(R.string.test_data))
            // simulate server response time after 3 sec
            response.setBodyDelay(3, TimeUnit.SECONDS)
            return response
        }
    }

    /**
     * in this test we will simulate a response from a local WebServer
     * using MockWebServer class
     */
    @Test
    fun simpleTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val repository = MockRepository(appContext)
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