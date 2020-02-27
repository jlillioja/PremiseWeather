package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.ConsolidatedWeather
import com.jlillioja.premiseweather.network.Location
import com.jlillioja.premiseweather.network.Weather

val testLocation1: Location = Location(
        "Seattle",
        "City",
        2490383,
        "47.603561,-122.329437"
)
val testLocation2: Location = Location(
        "Sydney",
        "City",
        1105779,
        "-33.869629, 151.206955"
)
val seattleWeather = Weather(
        listOf(ConsolidatedWeather("Rainy")),
        "Seattle"
)
