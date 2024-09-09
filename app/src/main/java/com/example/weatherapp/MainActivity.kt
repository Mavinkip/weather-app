package com.example.weatherapp

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var CITY: String = "nairobi,ke" // Set default city to Nairobi, Kenya
    val API: String = "2bccda4adbd64e5fcb506f56f4cdda49"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(Charsets.UTF_8)

                withContext(Dispatchers.Main) {
                    updateUI(response)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showError(e.message)
                }
            }
        }
    }

    private fun updateUI(response: String) {
        try {
            val jsonOb = JSONObject(response)
            val main = jsonOb.getJSONObject("main")
            val sys = jsonOb.getJSONObject("sys")
            val wind = jsonOb.getJSONObject("wind")
            val weather = jsonOb.getJSONArray("weather").getJSONObject(0)
            val updatedAt: Long = jsonOb.getLong("dt")
            val updatedAtText = "Updated at: " + SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(updatedAt * 1000)
            val temp = main.getString("temp") + "C"
            val tempMin = "Min Temp: " + main.getString("temp_min") + "C"
            val tempMax = "Max Temp: " + main.getString("temp_max") + "C"
            val pressure = main.getString("pressure")
            val humidity = main.getString("humidity")
            val sunrise: Long = sys.getLong("sunrise")
            val sunset: Long = sys.getLong("sunset")
            val windSpeed = wind.getString("speed")
            val weatherDescription = weather.getString("description").capitalize()
            val address = jsonOb.getString("name") + "," + sys.getString("country")

            // Updating UI
            findViewById<TextView>(R.id.address).text = address
            findViewById<TextView>(R.id.update_at).text = updatedAtText
            findViewById<TextView>(R.id.status).text = weatherDescription
            findViewById<TextView>(R.id.temp).text = temp
            findViewById<TextView>(R.id.temp_min).text = tempMin
            findViewById<TextView>(R.id.temp_max).text = tempMax
            findViewById<TextView>(R.id.Sunrise).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise * 1000))
            findViewById<TextView>(R.id.sunset).text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset * 1000))
            findViewById<TextView>(R.id.wind).text = windSpeed
            findViewById<TextView>(R.id.pressure).text = pressure
            findViewById<TextView>(R.id.humidity).text = humidity

            findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE
            findViewById<TextView>(R.id.errortext).visibility = View.GONE

        } catch (e: Exception) {
            showError(e.message)
        }
    }

    private fun showError(errorMessage: String?) {
        findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
        val errorTextView = findViewById<TextView>(R.id.errortext)
        errorTextView.text = errorMessage ?: "Something went wrong"
        errorTextView.visibility = View.VISIBLE
        findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
    }
}
