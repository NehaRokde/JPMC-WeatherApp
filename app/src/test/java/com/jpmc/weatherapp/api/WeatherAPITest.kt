package com.jpmc.weatherapp.api

import com.jpmc.weatherapp.Helper
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPITest {

    private lateinit var mockWebServer: MockWebServer
    lateinit var weatherApi: WeatherAPI

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        weatherApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherAPI::class.java)
    }

    @Test
    fun testGetWeatherBySearch_returnWeatherResponse() = runTest {
        val cityName = "New York"

        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = weatherApi.getWeatherBySearch(cityName)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body().toString().isEmpty())
        Assert.assertEquals("New York", response.body()!!.name)
    }

    @Test
    fun testGetWeatherBySearch_returnError() = runTest {
        val cityName = "New York"

        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("City Not Found")
        mockWebServer.enqueue(mockResponse)

        val response = weatherApi.getWeatherBySearch(cityName)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @Test
    fun testGetWeatherByCurrentLocation_returnWeatherResponse() = runTest {

        val latitude = 40.7143
        val longitude = -74.006

        val mockResponse = MockResponse()
        val content = Helper.readFileResource("/response.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = weatherApi.getWeatherByCurrentLocation(latitude, longitude)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.body().toString().isEmpty())
    }

    @Test
    fun testGetWeatherByCurrentLocation_returnError() = runTest {

        val latitude = 40.7143
        val longitude = -74.006

        val mockResponse = MockResponse()
        mockResponse.setResponseCode(404)
        mockResponse.setBody("City Not Found")
        mockWebServer.enqueue(mockResponse)

        val response = weatherApi.getWeatherByCurrentLocation(latitude, longitude)
        mockWebServer.takeRequest()

        Assert.assertEquals(false, response.isSuccessful)
        Assert.assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}