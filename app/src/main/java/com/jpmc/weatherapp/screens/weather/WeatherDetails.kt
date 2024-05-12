package com.jpmc.weatherapp.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jpmc.weatherapp.screens.home.HomeState
import com.app.openweatherapp.utils.helpers.EpochConverter
import com.jpmc.weatherapp.data.model.WeatherResponse
import com.jpmc.weatherapp.ui.theme.PrimaryColor
import com.jpmc.weatherapp.ui.theme.backgroundColor
import com.jpmc.weatherapp.utils.Constants
import com.jpmc.weatherapp.utils.convertUnixTimestampToDateString
import java.util.*


// TODO - can enhanced and added more UI for week hourly forecast, 1 week forecast.
@Composable
fun WeatherDetails(result: HomeState?) {

    if (result != null) {
        if (result.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        if (result.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = result.error
                )
            }
        }

        if (result.data != null) {
            DateSection(result.data)
            WeatherOverviewSection(result.data)
            DetailedWeatherInformationSection(result.data)
        }
    }
}

@Composable
fun DetailedWeatherInformationSection(weather: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherDetailRow("TEMP", "${weather.main.temp}${Constants.degree}")
                WeatherDetailRow("FEELS LIKE", "${weather.main.feelsLike}${Constants.degree}")
                WeatherDetailRow("HUMIDITY", "${weather.main.humidity}%")
                WeatherDetailRow("CLOUDINESS", "${weather.clouds.all}%")
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherDetailRow(
                    "SUNRISE",
                    "${EpochConverter.readTimestamp(weather.sys.sunrise)}AM"
                )
                WeatherDetailRow("SUNSET", "${EpochConverter.readTimestamp(weather.sys.sunset)}PM")
                WeatherDetailRow("WIND", "${weather.wind.speed}KM")
                WeatherDetailRow("PRESSURE", "${weather.wind.deg}")
            }
        }
    }
}

@Composable
private fun WeatherDetailRow(title: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun WeatherOverviewSection(weatherResponse: WeatherResponse) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White,
        contentColor = Color.Black,
        modifier = Modifier.padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${weatherResponse.name}, ${(weatherResponse.sys.country).toUpperCase()}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                WeatherIcon(iconId = weatherResponse.weather!![0].icon)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${weatherResponse.main.temp}${Constants.degree}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Text(
                text = weatherResponse.weather?.get(0)?.description?.let {
                    it.substring(0, 1).uppercase(Locale.getDefault()) + it.substring(1)
                } ?: "",
                fontSize = 16.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                Text(
                    text = "H:${weatherResponse.main.tempMax}°",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "L:${weatherResponse.main.tempMin}°",
                    fontSize = 14.sp
                )
            }
        }
    }
}


@Composable
fun DateSection(weatherResponse: WeatherResponse) {
    Box(
        modifier = Modifier
            .fillMaxWidth().padding(top = 16.dp),
    contentAlignment = Alignment.Center,
    ) {
        Text(
            text = convertUnixTimestampToDateString(weatherResponse.dt),
            color = PrimaryColor,
            fontFamily = FontFamily.Serif,
            fontSize = 19.sp
        )
    }
}
