Weather App
Overview
The Weather App provides real-time weather information for any city around the world. It fetches weather data from the OpenWeatherMap API and displays key details such as temperature, weather description, wind speed, pressure, humidity, sunrise, and sunset times. Users can input a city name to get the latest weather information for that location.

Features
Real-Time Weather Data: Fetches and displays current weather information for a specified city.
City Update: Allows users to enter and update the city name to retrieve weather data for different locations.
Dynamic UI Updates: Updates the user interface with the latest weather data and handles errors gracefully.
API Integration: Utilizes the OpenWeatherMap API to get weather details.
Getting Started
Prerequisites
Android Studio or any compatible IDE
Kotlin SDK
OpenWeatherMap API Key
Setup
Clone the Repository

bash
Copy code
git clone https://github.com/yourusername/weather-app.git
cd weather-app
Add Your API Key

Open the MainActivity.kt file and replace the API variable value with your own OpenWeatherMap API key:

kotlin
Copy code
val API: String = "YOUR_API_KEY"
Build and Run

Open the project in Android Studio, build the project, and run it on an emulator or physical device.

Usage
Launch the App

The app will start with the default city set to Kapsabet, Kenya.

Update City

Enter the name of a city in the input field.
Click the "Update City" button.
The app will fetch and display weather information for the specified city.
How It Works
The app uses the OpenWeatherMap API to fetch weather data. Here's a brief overview of how the app works:

Fetch Weather Data

When the app starts or when the user updates the city, the fetchWeatherData function is called. This function sends an HTTP GET request to the OpenWeatherMap API endpoint with the city name and API key.

kotlin
Copy code
val response = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&units=metric&appid=$API").readText(Charsets.UTF_8)
Parse API Response

The response from the API is in JSON format. The updateUI function parses this JSON response to extract relevant weather information, such as temperature, weather description, wind speed, and more.

kotlin
Copy code
val jsonOb = JSONObject(response)
val main = jsonOb.getJSONObject("main")
val sys = jsonOb.getJSONObject("sys")
val wind = jsonOb.getJSONObject("wind")
val weather = jsonOb.getJSONArray("weather").getJSONObject(0)
Update UI

The extracted data is then used to update the user interface elements in the app. The UI is updated with the weather details and any error messages if the request fails.

kotlin
Copy code
findViewById<TextView>(R.id.address).text = address
findViewById<TextView>(R.id.update_at).text = updatedAtText
Error Handling
If there's an issue with fetching the weather data (e.g., network error or invalid city name), the app displays an appropriate error message to the user.

Contributing
Feel free to contribute to the project by opening issues, submitting pull requests, or suggesting improvements. Please follow the project's code of conduct and contribution guidelines.

License
This project is licensed under the MIT License. See the LICENSE file for details.
