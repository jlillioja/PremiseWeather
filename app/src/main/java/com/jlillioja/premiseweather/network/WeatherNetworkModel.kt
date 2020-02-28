package com.jlillioja.premiseweather.network

import com.jlillioja.premiseweather.Weather

data class WeatherNetworkModel(
        val consolidated_weather: List<ConsolidatedWeather>,
        val title: String
) {
    fun toWeatherList(): List<Weather> {
        return consolidated_weather.map {
            Weather(
                    title,
                    it.applicable_date,
                    convertCentrigradeToFahrenheit(it.the_temp),
                    it.air_pressure,
                    it.humidity,
                    it.weather_state_name,
                    it.predictability
            )
        }
    }

    private fun convertCentrigradeToFahrenheit(degrees: Double) = ((degrees*(9.0/5.0))+32)
}

data class ConsolidatedWeather(
        val weather_state_name: String,
        val the_temp: Double,              // degrees centigrade
        val air_pressure: Double,          // in mbar
        val humidity: Double,              // percentage
        val predictability: Int,        // percentage
        val applicable_date: String     // YYYY-MM-DD
)