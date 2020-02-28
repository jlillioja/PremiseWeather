package com.jlillioja.premiseweather.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MetaWeatherInterface {

    @GET("/api/location/search/")
    fun getLocationBySearch(
            @Query("query") search: String
    ): Observable<List<Location>>

    @GET("/api/location/search/?lattlong={latitude},{longitude}")
    fun getLocationByLatLong(
            @Path("latitude") latitude: Double,
            @Path("longitude")longitude: Double
    ): Observable<List<Location>>

    @GET("api/location/{woeid}")
    fun getWeatherByWhereOnEarthId(
            @Path("woeid") woeid: Int
    ): Observable<WeatherNetworkModel>

}