package com.jpmc.weatherapp.viewmodel

import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmc.weatherapp.data.location.LocationTracker
import com.jpmc.weatherapp.repository.WeatherRepository
import com.jpmc.weatherapp.screens.home.HomeState
import com.jpmc.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * ViewModel responsible for managing weather data based on the user's current location.
 *
 * @param locationTracker - for retrieving the user's current location.
 * @param weatherRepository - fetching weather data.
 */
@HiltViewModel
class LocationWeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val currentLocationWeatherData: MutableState<HomeState?> = mutableStateOf(null)

    private fun getWeatherByCurrentLocation(latitude: Double, longitude: Double) =
        viewModelScope.launch {
            when (val result = weatherRepository.getWeatherByLocation(latitude, longitude)) {
                is NetworkResult.Loading -> {
                    currentLocationWeatherData.value = HomeState(isLoading = true)

                }
                is NetworkResult.Success -> {
                    result.data?.let {
                        currentLocationWeatherData.value = HomeState(data = result.data)
                    }
                }
                is NetworkResult.Error -> {
                    currentLocationWeatherData.value = HomeState(error = "Something went wrong.")
                }
            }
        }

    var currentLocation by mutableStateOf<Location?>(null)
    fun getWeatherByLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation()
            if (currentLocation != null) {
                getWeatherByCurrentLocation(currentLocation!!.latitude, currentLocation!!.longitude)
            } else {
                Log.e("Error", "Error in CurrentLocation")
            }
        }
    }
}