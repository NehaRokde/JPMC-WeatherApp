package com.jpmc.weatherapp.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jpmc.weatherapp.screens.weather.WeatherDetails
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.jpmc.weatherapp.ui.theme.PrimaryColor
import com.jpmc.weatherapp.viewmodel.LocationWeatherViewModel

/**
 * Composable function representing the home screen of the weather app.
 *
 * @param navController - for navigating to other screens.
 * @param locationWeatherViewModel - for managing weather data based on the user's current location.
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    locationWeatherViewModel: LocationWeatherViewModel
) {
    val scrollStateScreen = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .verticalScroll(scrollStateScreen)
    ) {
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 10.dp
            )
        ) {
            Button(
                onClick = { navController.navigate("search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevation(8.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = PrimaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(
                    "Select US City",
                    modifier = Modifier.padding(8.dp)
                )
            }

            // current location weather data on WeatherScreen
            val result = locationWeatherViewModel.currentLocationWeatherData.value
            WeatherDetails(result)
        }
    }
}