package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.MetaWeatherInterface
import com.jlillioja.premiseweather.network.Weather
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

interface WeatherProvider {
    fun getWeatherBySearch(search: String): Observable<Weather>
}

class WeatherProviderImpl(var metaWeatherInterface: MetaWeatherInterface): WeatherProvider {
    override fun getWeatherBySearch(search: String): Observable<Weather> {
        return metaWeatherInterface
                .getLocationBySearch(search)
                .flatMap { locations ->
                    val closestLocation = locations.firstOrNull()
                    if (closestLocation == null) {
                        Observable.error<Weather>(NoLocationsFound())
                    } else {
                        metaWeatherInterface.getWeatherByWhereOnEarthId(closestLocation.woeid)
                    }
                }
                .subscribeOn(Schedulers.io())
    }
}

class NoLocationsFound : Exception()