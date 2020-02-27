package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.MetaWeatherInterface
import io.reactivex.Observable
import org.junit.Test

import org.junit.Before
import org.mockito.Mockito.*
import java.lang.Thread.sleep

class WeatherProviderTest {
    lateinit var weatherProvider: WeatherProvider
    lateinit var mockMetaWeatherInterface: MetaWeatherInterface

    @Before
    fun setup() {
        mockMetaWeatherInterface = mock(MetaWeatherInterface::class.java)
        `when`(mockMetaWeatherInterface.getLocationBySearch(testLocation1.title))
                .thenReturn(Observable.just(listOf(testLocation1, testLocation2)))
        `when`(mockMetaWeatherInterface.getWeatherByWhereOnEarthId(testLocation1.woeid))
                .thenReturn(Observable.just(seattleWeather))
        weatherProvider = WeatherProviderImpl(mockMetaWeatherInterface)
    }

    @Test
    fun getWeatherBySearch_queriesApiForLocation() {
        weatherProvider.getWeatherBySearch(testLocation1.title).test()

        sleep(100)

        verify(mockMetaWeatherInterface).getLocationBySearch(testLocation1.title)
    }

    @Test
    fun getWeatherBySearch_queriesWeatherForFirstLocation() {
        weatherProvider.getWeatherBySearch(testLocation1.title).test()

        sleep(100)

        verify(mockMetaWeatherInterface).getWeatherByWhereOnEarthId(testLocation1.woeid)
    }

    @Test
    fun getWeatherBySearch_returnsWeatherForFirstLocation() {
        val tester = weatherProvider.getWeatherBySearch(testLocation1.title).test()

        sleep(100)

        verify(mockMetaWeatherInterface).getWeatherByWhereOnEarthId(testLocation1.woeid)
        tester.assertValue(seattleWeather)
    }
}
