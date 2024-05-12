<h2>Overview</h2>
 App built using Jetpack Compose and following the MVVM architecture pattern. 
It allows users to search for US cities, displays relevant information along with weather icons. 
Additionally, it requests location access to retrieve weather data by default if the user grants permission.

<h2>Tech Stack</h2>

<li>Coding Language: Kotlin</li>
<li>Architecture Pattern: MVVM</li>
<li>Network Library: Retrofit</li>
<li>Unit Test: JUnit</li>
<li>Network Concurrency: Kotlin Coroutines</li>
<li>Unit Test: junit, mockk, Mockito, mockwebserver</li>
<li>UI: Jetpack Compose</li>
<li>Dependency Injection: Dagger-Hilt</li>
<li>Navigation: Jetpack Navigation</li>

<h2>Functionality</h2>
<h4>Home Screen (on Launch of app)</h4>
<li>Upon launching the home screen, the app requests location permissions from the user. </li>
<li>If permission is granted, the app displays weather information based on the user's current location. </li>
<li>If the user denies location permission, a toast message is shown along with a search button, allowing the user to input the city name manually.</li>
<h4>Search CityScreen</h4>
<li>Navigation from Home Screen to Search Screen using Jetpack Navigation</li>
<li>Upon searching for a city, the user can view weather details such as the current day and time, location name, 
    current weather conditions including icons, "feels like" temperature, as well as the low and high temperatures for the day. </li>
<li>Additionally, the app provides further details such as wind speed, humidity, sunrise, and sunset times.</li>


</li>
<h4>Testing</h4>
<li>Unit tests added for the WeatherAPI to validate its functionality in both success and error scenarios using MockWebServer. </li>
<li>Test cases implemented for the WeatherRepository to validate mock response for location and city searches using Mockito. </li>
<li>Few test cases have also been incorporated to for the Search screen's Compose UI.</li>

</p></p>
<img src="https://github.com/NehaRokde/JPMC-WeatherApp/assets/1868114/b5836871-a315-410f-a310-a4e721278e9d" width="200">
<img src = "https://github.com/NehaRokde/JPMC-WeatherApp/assets/1868114/96a0b7ff-d60a-415c-8097-30100fe17881" width = "200">
</p></p>
<img src = "https://github.com/NehaRokde/JPMC-WeatherApp/assets/1868114/345f99eb-b7aa-454d-a6c0-ae09c150ed6a" width = "200">
<img src = "https://github.com/NehaRokde/JPMC-WeatherApp/assets/1868114/c5bc4be4-9672-4e67-b0ac-779d70f7d9d1" width = "200">

