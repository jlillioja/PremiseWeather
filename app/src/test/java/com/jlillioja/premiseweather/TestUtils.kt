package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.ConsolidatedWeather
import com.jlillioja.premiseweather.network.LocationInfo
import com.jlillioja.premiseweather.network.WeatherNetworkModel

val testLocation1: LocationInfo = LocationInfo(
        "Seattle",
        "City",
        2490383,
        "47.603561,-122.329437"
)
val testLocation2: LocationInfo = LocationInfo(
        "Sydney",
        "City",
        1105779,
        "-33.869629, 151.206955"
)
val seattleWeatherNetworkModel = WeatherNetworkModel(
        listOf(ConsolidatedWeather(
                "Rainy",
                8.385,
                1022.5,
                72.0,
                75,
                "2020-02-28"

        )),
        "Seattle",
        "47.6062,122.3321"
)
val seattleWeather = seattleWeatherNetworkModel.toWeatherList()