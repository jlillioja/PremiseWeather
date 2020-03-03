package com.jlillioja.premiseweather

import com.google.android.gms.maps.model.LatLng

data class WeatherList(
        val locationTitle: String,
        val location: LatLng,
        val forecast: List<WeatherForDay>
)

data class WeatherForDay(
        val location: String,
        val day: String,
        val temperature: Double,
        val pressure: Double,
        val humidity: Double,
        val conditions: String,
        val likelihood: Int
)