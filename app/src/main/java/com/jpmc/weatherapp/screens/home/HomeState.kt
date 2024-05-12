package com.jpmc.weatherapp.screens.home

import com.jpmc.weatherapp.data.model.WeatherResponse

// class to handle state of api result
data class HomeState(
    val isLoading: Boolean = false,
    val data: WeatherResponse? = null,
    val error: String = ""
)
