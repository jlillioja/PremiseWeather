package com.jlillioja.premiseweather.network

data class Weather(
        val consolidated_weather: List<ConsolidatedWeather>,
        val title: String
)

data class ConsolidatedWeather(
        val weather_state_name: String
)