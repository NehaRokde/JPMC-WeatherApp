package com.jpmc.weatherapp.repository

import com.jpmc.weatherapp.api.WeatherAPI
import com.jpmc.weatherapp.data.model.WeatherResponse
import com.jpmc.weatherapp.utils.NetworkResult
import javax.inject.Inject
/**
* Repository class responsible for fetching weather data from the WeatherAPI.
*
*/
class WeatherRepository @Inject constructor(private val weatherAPI: WeatherAPI) {

    suspend fun getWeatherBySearch(cityName: String): NetworkResult<WeatherResponse> {

        val response = weatherAPI.getWeatherBySearch(cityName)

        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(data = responseBody)
            } else {
                NetworkResult.Error(message = response.message())
            }
        } else {
            NetworkResult.Error(message = response.code().toString())
        }
    }

    suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherResponse> {
        val response = weatherAPI.getWeatherByCurrentLocation(latitude, longitude)
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(data = responseBody)
            } else {
                NetworkResult.Error(message = response.message())
            }
        } else {
            NetworkResult.Error(message = response.code().toString())
        }
    }

}