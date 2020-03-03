package com.jlillioja.premiseweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class WeatherListAdapterTest {
    lateinit var weatherListAdapter: WeatherListAdapter
    lateinit var mockActivity: MainActivity
    lateinit var mockLayoutInflater: LayoutInflater
    lateinit var mockView: View

    @Before
    fun setup() {
        mockActivity = mock(MainActivity::class.java)
        mockLayoutInflater = mock(LayoutInflater::class.java)
        mockView = mock(View::class.java)

        `when`(mockActivity.layoutInflater).thenReturn(mockLayoutInflater)
        `when`(mockLayoutInflater.inflate(anyInt(), any<ViewGroup>(), anyBoolean())).thenReturn(mockView)

        weatherListAdapter = WeatherListAdapter(mockActivity)
    }

    @Test
    fun getCount_isLengthOfWeatherList() {
        weatherListAdapter.weather = null

        assertThat(weatherListAdapter.count, equalTo(0))

        weatherListAdapter.weather = seattleWeather

        assertThat(weatherListAdapter.count, equalTo(1))
    }

    @Test
    fun getView_returnsListItem_CorrectlySetsFields_DoesNotAttachToRoot() {
        val mockDayTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.day)).thenReturn(mockDayTextView)
        val mockTemperatureTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.temperature)).thenReturn(mockTemperatureTextView)
        val mockPressureTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.pressure)).thenReturn(mockPressureTextView)
        val mockHumidityTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.humidity)).thenReturn(mockHumidityTextView)
        val mockConditionsTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.conditions)).thenReturn(mockConditionsTextView)
        val mockLikelihoodTextView = mock(TextView::class.java)
        `when`(mockView.findViewById<TextView>(R.id.likelihood)).thenReturn(mockLikelihoodTextView)

        weatherListAdapter.weather = seattleWeather
        weatherListAdapter.getView(0, null, null)

        verify(mockLayoutInflater).inflate(R.layout.weather_list_item, null, false)

        verify(mockDayTextView).text = seattleWeather.forecast[0].day
        verify(mockTemperatureTextView).text = "47"
        verify(mockPressureTextView).text = "1023"
        verify(mockHumidityTextView).text = "72"
        verify(mockConditionsTextView).text = "Rainy"
        verify(mockLikelihoodTextView).text = "75"
    }
}