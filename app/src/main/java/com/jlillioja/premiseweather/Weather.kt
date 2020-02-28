package com.jlillioja.premiseweather

data class Weather(
        val location: String,
        val day: String,
        val temperature: Double,
        val pressure: Double,
        val humidity: Double,
        val conditions: String,
        val likelihood: Int
)