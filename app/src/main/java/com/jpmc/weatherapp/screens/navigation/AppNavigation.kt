package com.jpmc.weatherapp.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jpmc.weatherapp.screens.home.HomeScreen
import com.jpmc.weatherapp.screens.search.SearchScreen
import com.jpmc.weatherapp.viewmodel.LocationWeatherViewModel

/**
 * Composable function for navigation logic of the weather app.
 *
 * @param locationWeatherViewModel - for managing weather data based on the user's current location.
 */
@Composable
fun AppNavigation(locationWeatherViewModel: LocationWeatherViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, locationWeatherViewModel)
        }
        composable("search") {
            SearchScreen(
                onHome = { navController.popBackStack() }
            )
        }
    }
}