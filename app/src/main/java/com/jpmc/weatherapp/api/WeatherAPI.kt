package com.jpmc.weatherapp.api

import com.jpmc.weatherapp.data.model.WeatherResponse
import com.jpmc.weatherapp.utils.Constants
import com.jpmc.weatherapp.utils.Constants.UNITS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to define the API endpoints for fetching weather data.
 */
interface WeatherAPI {
    /**
     * Fetches weather data based on the city searched by the user.
     *
     * @param cityName - city for which weather data is to be fetched.
     * @param apiKey  -  API key , Defaults to [Constants.API_KEY].
     * @param units - temperature measurement. Defaults to [Constants.UNITS].
     */
    // TODO - Can provide users with a toggle button to
    //  select their preferred temperature format.
    //  Currently, the temperature display is static, set to Celsius.

    @GET("data/2.5/weather")
    suspend fun getWeatherBySearch(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = Constants.API_KEY,
        @Query("units") units: String = UNITS
    ): Response<WeatherResponse>

    /**
     * Fetches weather data based on the user's current location.
     *
     * @param latitude - latitude coordinate of the user's location.
     * @param longitude - longitude coordinate of the user's location.
     * @param apiKey  - The API key . Defaults to [Constants.API_KEY].
     * @param units  temperature measurement. Defaults to [Constants.UNITS].
     */
    @GET("data/2.5/weather")
    suspend fun getWeatherByCurrentLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Constants.API_KEY,
        @Query("units") units: String = Constants.UNITS,
    ): Response<WeatherResponse>
}