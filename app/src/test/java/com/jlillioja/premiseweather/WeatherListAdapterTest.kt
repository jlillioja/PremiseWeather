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
    fun getCount_isLengthOfWeatherListPlus1() {
        weatherListAdapter.weather = listOf()

        assertThat(weatherListAdapter.count, equalTo(1))

        weatherListAdapter.weather = seattleWeather

        assertThat(weatherListAdapter.count, equalTo(2))
    }

    @Test
    fun getView_returnsHeaderRowForFirstPosition_DoesNotAttachToRoot() {
        weatherListAdapter.getView(0, null, null)

        verify(mockLayoutInflater).inflate(R.layout.weather_list_header, null, false)
    }

    @Test
    fun getView_returnsListItemForOtherPositions_CorrectlySetsFields_DoesNotAttachToRoot() {
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
        weatherListAdapter.getView(1, null, null)

        verify(mockLayoutInflater).inflate(R.layout.weather_list_item, null, false)

        verify(mockDayTextView).text = seattleWeather[0].day
        verify(mockTemperatureTextView).text = "47"
        verify(mockPressureTextView).text = "1023"
        verify(mockHumidityTextView).text = "72"
        verify(mockConditionsTextView).text = "Rainy"
        verify(mockLikelihoodTextView).text = "75"
    }
}