package com.jpmc.weatherapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jpmc.weatherapp.repository.WeatherRepository
import com.jpmc.weatherapp.screens.home.HomeState
import com.jpmc.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing city weather data.
 * @param weatherRepository - for fetching weather data.
 */
@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val weatherData: MutableState<HomeState?> = mutableStateOf(null)

    fun getWeatherData(cityName: String) = viewModelScope.launch {

        when (val result = weatherRepository.getWeatherBySearch(cityName)) {
            is NetworkResult.Loading -> {
                weatherData.value = HomeState(isLoading = true)

            }
            is NetworkResult.Success -> {
                result.data?.let {
                    weatherData.value = HomeState(data = result.data)
                }
            }
            is NetworkResult.Error -> {
                if (result.message == "404") {
                    weatherData.value = HomeState(error = "Please enter valid US city name.")
                } else {
                    weatherData.value = HomeState(error = "Something went wrong!!!")
                }
            }
        }
    }

}
