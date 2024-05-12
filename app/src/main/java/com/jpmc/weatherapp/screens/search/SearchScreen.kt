package com.jpmc.weatherapp.screens.search


import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jpmc.weatherapp.screens.weather.WeatherDetails
import com.jpmc.weatherapp.ui.theme.PrimaryColor
import com.jpmc.weatherapp.ui.theme.backgroundColor
import com.jpmc.weatherapp.utils.Constants
import com.jpmc.weatherapp.viewmodel.CityWeatherViewModel
import kotlinx.coroutines.*

/**
 * Composable function for the search screen.
 * @param onHome Callback to navigate back to the home screen.
 * @param viewModel - instance of [CityWeatherViewModel].
 */
@Composable
fun SearchScreen(onHome: () -> Unit, viewModel: CityWeatherViewModel = hiltViewModel()) {

    // Mutable state to hold the entered city name
    val cityName: MutableState<String> = remember {
        mutableStateOf("")
    }
    // Weather data from the view model
    val result = viewModel.weatherData.value
    // Scroll state for the screen
    val scrollStateScreen = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollStateScreen)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                SearchTextField(
                    // TODO - to show US cities only, hardcoded Country code
                    //  there  are multiple ways to do it, but for sample app using easy approach
                    onSearch = { city ->
                        if (city.isNotEmpty())
                            viewModel.getWeatherData(city + ", ${Constants.COUNTRY_CODE}")
                    }
                )
                // WeatherDetails composable to display weather information
                WeatherDetails(result)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    onSearch: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var job by remember { mutableStateOf<Job?>(null) }

    OutlinedTextField(
        value = searchText,
        onValueChange = { newText ->
            searchText = newText
            job?.cancel()
            job = CoroutineScope(Dispatchers.Default).launch {
                delay(1000)
                onSearch(searchText)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Search here...") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            focusedBorderColor = PrimaryColor,
            unfocusedBorderColor = PrimaryColor,
            focusedLabelColor = PrimaryColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                onSearch(searchText)
            }
        )
    )
}