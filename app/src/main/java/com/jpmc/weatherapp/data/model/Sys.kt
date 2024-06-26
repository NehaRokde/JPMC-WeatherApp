package com.jpmc.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("country")
    val country: String = "",
    @SerializedName("sunrise")
    val sunrise: Int = 0,
    @SerializedName("sunset")
    val sunset: Int = 0
)