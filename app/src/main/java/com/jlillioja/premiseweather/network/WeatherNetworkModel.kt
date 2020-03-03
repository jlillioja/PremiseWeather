package com.jlillioja.premiseweather.network

import com.google.android.gms.maps.model.LatLng
import com.jlillioja.premiseweather.WeatherForDay
import com.jlillioja.premiseweather.WeatherList

data class WeatherNetworkModel(
        val consolidated_weather: List<ConsolidatedWeather>,
        val title: String,
        val latt_long: String
) {
    fun toWeatherList(): WeatherList {
        return WeatherList(
                locationTitle = title,
                location = parseLatLong(latt_long),
                forecast = consolidated_weather.map {
                    WeatherForDay(
                            title,
                            it.applicable_date,
                            convertCentrigradeToFahrenheit(it.the_temp),
                            it.air_pressure,
                            it.humidity,
                            it.weather_state_name,
                            it.predictability
                    )
                }
        )
    }

    private fun convertCentrigradeToFahrenheit(degrees: Double) = ((degrees * (9.0 / 5.0)) + 32)
    private fun parseLatLong(latt_long: String): LatLng = latt_long
            .split(',')
            .map { it.toDouble() }
            .run { LatLng(get(0), get(1)) }
}

data class ConsolidatedWeather(
        val weather_state_name: String,
        val the_temp: Double,              // degrees centigrade
        val air_pressure: Double,          // in mbar
        val humidity: Double,              // percentage
        val predictability: Int,        // percentage
        val applicable_date: String     // YYYY-MM-DD
)