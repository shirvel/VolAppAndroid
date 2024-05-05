package com.example.app.Modules.weather

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class Weather(private val context: Context, private val activity: Activity) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001
    private val API_URL = "https://api.sunrise-sunset.org/json?"

    init {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun getCurrentLocation(callback: (Location) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            // TODO Handle permission denied
            return
        }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest.create(),
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult?.lastLocation?.let {
                        fusedLocationClient.removeLocationUpdates(this)
                        callback(it)
                    }
                }
            },
            Looper.getMainLooper()
        )
    }

    fun determineDayOrNight(
        callback: (String) -> Unit
    ) {
        getCurrentLocation { location ->
            GlobalScope.launch(Dispatchers.IO) {
                val latitude = location.latitude
                val longitude = location.longitude
                val url = URL("$API_URL&lat=$latitude&lng=$longitude")
                val connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 15000
                connection.requestMethod = "GET"

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    val sunriseStr = JSONObject(response.toString())
                        .getJSONObject("results").getString("sunrise")
                    val sunsetStr = JSONObject(response.toString())
                        .getJSONObject("results").getString("sunset")

                    val formatter = DateTimeFormatter.ofPattern("h:mm:ss a")
                        .withZone(ZoneOffset.UTC)

                    val sunriseHour = LocalTime.parse(sunriseStr, formatter)
                    val sunsetHour = LocalTime.parse(sunsetStr, formatter)
                    val currentTime = LocalTime.now(ZoneOffset.UTC)

                    val isDay = currentTime.isAfter(sunriseHour) && currentTime.isBefore(sunsetHour)

                    val dayOrNight = if (isDay) "Morning" else "Evening"
                    callback(dayOrNight)
                }
            }
        }
    }

    private fun getHourOfDay(timeStr: String): Int {
        val dateFormat = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
        val date = dateFormat.parse(timeStr)
        val calendar = Calendar.getInstance()
        if (date != null) {
            calendar.time = date
        }
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

}