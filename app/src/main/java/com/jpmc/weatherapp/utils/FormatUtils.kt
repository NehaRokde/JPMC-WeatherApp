package com.jpmc.weatherapp.utils

import com.jpmc.weatherapp.R
import java.text.SimpleDateFormat
import java.util.*

fun convertUnixTimestampToDateString(unixTimestamp: Int): String {
    val date = Date(unixTimestamp * 1000L) // Convert seconds to milliseconds
    val dateFormat = SimpleDateFormat("MMMM d, hh:mm a", Locale.getDefault())
    return dateFormat.format(date)
}

fun getImageFromUrl(url: String): Int {
    return when (url) {
//        "01d" -> R.drawable.ic_sun
//        "01n" -> R.drawable.ic_moon
//        "02d" -> R.drawable.ic_few_clouds
//        "02n" -> R.drawable.ic_night_clouds
//        "03d", "03n" -> R.drawable.ic_scattered_clouds
//        "04d", "04n" -> R.drawable.ic_broken_clouds
//        "09d", "09n" -> R.drawable.ic_shower_rain
//        "10d", "10n" -> R.drawable.ic_rain
//        "11d", "11n" -> R.drawable.ic_thunderstorm
//        "13d", "13n" -> R.drawable.ic_snow
//        "50d", "50n" -> R.drawable.ic_mist
        else -> R.drawable.ic_launcher_foreground
    }
}
