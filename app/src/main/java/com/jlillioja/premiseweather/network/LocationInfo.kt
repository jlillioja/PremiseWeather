package com.jlillioja.premiseweather.network

data class LocationInfo(
        val title: String,
        val locationType: String,
        val woeid: Int,
        val lattLong: String
)