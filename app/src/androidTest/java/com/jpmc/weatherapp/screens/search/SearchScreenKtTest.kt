package com.jpmc.weatherapp.screens.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTextInput
import com.jpmc.weatherapp.viewmodel.CityWeatherViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SearchScreenKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchScreen() {
        val mockViewModel = mock(CityWeatherViewModel::class.java)

        val expectedCityName = "New York"

        composeTestRule.setContent {
            SearchScreen(onHome = {}, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithContentDescription("Search here...").performTextInput(expectedCityName)

        verify(mockViewModel).getWeatherData(expectedCityName)
    }
}