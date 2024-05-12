package com.jpmc.weatherapp

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.jpmc.weatherapp.screens.navigation.AppNavigation
import com.jpmc.weatherapp.ui.theme.JPMCWeatherAppTheme
import com.jpmc.weatherapp.ui.theme.PrimaryColor
import com.jpmc.weatherapp.ui.theme.backgroundColor
import com.jpmc.weatherapp.viewmodel.LocationWeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val locationWeatherViewModel: LocationWeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get permission status
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val deniedPermissions = permissions.filterValues { !it }
            if (deniedPermissions.isNotEmpty()) {
                Toast.makeText(
                    this,
                    "To get current location allow location permission or search city using search box.",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                // All permissions granted
                // Proceed with the desired operation
                locationWeatherViewModel.getWeatherByLocation()
            }

        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )

        setContent {
            JPMCWeatherAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(title = {
                            Text(text = "Weather Watch", fontSize = 20.sp)
                        }, backgroundColor = PrimaryColor, contentColor = Color.White)
                    }
                ) {
                    Box(modifier = Modifier.padding(it).background(backgroundColor)) {
                        AppNavigation(locationWeatherViewModel = locationWeatherViewModel)
                    }
                }
            }
        }
    }
}