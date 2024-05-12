package com.jpmc.weatherapp.repository

import com.jpmc.weatherapp.api.WeatherAPI
import com.jpmc.weatherapp.data.model.*
import com.jpmc.weatherapp.utils.NetworkResult
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

internal class WeatherRepositoryTest {
    @Mock
    lateinit var weatherAPI: WeatherAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetWeatherBySearch_expectedWeatherResponse() = runBlocking {
        val cityName = "New York"
        val expectedWeatherResponse = WeatherResponse(
            coord = Coord(-74.006, 40.7143),
            weather = listOf(Weather("10d", "light rain", "Rain", 500)),
            base = "stations",
            main = Main(
                temp = 9.67,
                tempMin = 7.76,
                humidity = 75,
                pressure = 1012,
                feelsLike = 7.21,
                tempMax = 10.68
            ),
            visibility = 10000,
            wind = Wind(261, 4.11, 3.74),
            clouds = Clouds(100),
            dt = 1715508971,
            sys = Sys("US", 1715506889, 1715558603),
            timezone = -10800,
            id = 5128581,
            name = "New York",
            cod = 200
        )

        Mockito.`when`(weatherAPI.getWeatherBySearch(cityName))
            .thenReturn(Response.success(expectedWeatherResponse))

        val sut = WeatherRepository(weatherAPI)
        val result = sut.getWeatherBySearch(cityName)

        assertEquals(true, result is NetworkResult.Success)
        assertEquals("New York", result.data!!.name)
        assertEquals("light rain", result.data!!.weather!![0].description)
    }

    @Test
    fun testGetWeatherBySearch_expectedError() = runBlocking {
        val cityName = "Nt"

        Mockito.`when`(weatherAPI.getWeatherBySearch(cityName))
            .thenReturn(Response.error(404, "city not found".toResponseBody()))

        val sut = WeatherRepository(weatherAPI)
        val result = sut.getWeatherBySearch(cityName)

        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("404", result.message)
    }

    @Test
    fun testGetWeatherByLocation_expectedWeatherResponse() = runBlocking {

        val latitude = -65.9988
        val longitude = 46.5001
        val expectedWeatherResponse = WeatherResponse(
            coord = Coord(-74.006, 40.7143),
            weather = listOf(Weather("10d", "light rain", "Rain", 500)),
            base = "stations",
            main = Main(
                temp = 9.67,
                tempMin = 7.76,
                humidity = 75,
                pressure = 1012,
                feelsLike = 7.21,
                tempMax = 10.68
            ),
            visibility = 10000,
            wind = Wind(261, 4.11, 3.74),
            clouds = Clouds(100),
            dt = 1715508971,
            sys = Sys("US", 1715506889, 1715558603),
            timezone = -10800,
            id = 5128581,
            name = "New York",
            cod = 200
        )

        Mockito.`when`(weatherAPI.getWeatherByCurrentLocation(latitude, longitude))
            .thenReturn(Response.success(expectedWeatherResponse))

        val sut = WeatherRepository(weatherAPI)
        val result = sut.getWeatherByLocation(latitude, longitude)

        Assert.assertEquals(true, result is NetworkResult.Success)
        Assert.assertEquals("New York", result.data!!.name)
        Assert.assertEquals("light rain", result.data!!.weather!![0].description)
        val delta = 0.01
        Assert.assertEquals(7.21, result.data!!.main.feelsLike, delta)
    }


    @Test
    fun testGetWeatherByLocation_expectedError() = runBlocking {
        val latitude = -65.9988
        val longitude = 46.5001

        Mockito.`when`(weatherAPI.getWeatherByCurrentLocation(latitude, longitude))
            .thenReturn(Response.error(404, "city not found".toResponseBody()))

        val sut = WeatherRepository(weatherAPI)
        val result = sut.getWeatherByLocation(latitude, longitude)

        Assert.assertEquals(true, result is NetworkResult.Error)
        Assert.assertEquals("404", result.message)
    }
}