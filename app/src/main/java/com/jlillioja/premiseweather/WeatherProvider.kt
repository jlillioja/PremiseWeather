package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.MetaWeatherInterface
import com.jlillioja.premiseweather.network.WeatherNetworkModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

interface WeatherProvider {
    fun getWeatherBySearch(search: String): Observable<WeatherList>
}

class WeatherProviderImpl(var metaWeatherInterface: MetaWeatherInterface): WeatherProvider {
    override fun getWeatherBySearch(search: String): Observable<WeatherList> {
        return metaWeatherInterface
                .getLocationBySearch(search)
                .flatMap { locations ->
                    val closestLocation = locations.firstOrNull()
                    if (closestLocation == null) {
                        Observable.error<WeatherNetworkModel>(NoLocationsFound())
                    } else {
                        metaWeatherInterface.getWeatherByWhereOnEarthId(closestLocation.woeid)
                    }
                }
                .map { it.toWeatherList() }
                .subscribeOn(Schedulers.io())
    }
}

class NoLocationsFound : Exception("No locations found")