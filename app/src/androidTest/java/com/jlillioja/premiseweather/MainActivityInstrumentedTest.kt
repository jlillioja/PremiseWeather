package com.jlillioja.premiseweather

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.jlillioja.premiseweather.network.ConsolidatedWeather
import com.jlillioja.premiseweather.network.Weather
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Thread.sleep
import javax.inject.Inject

class MainActivityInstrumentedTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Inject
    lateinit var mockWeatherProvider: WeatherProvider

    @Before
    fun setup() {
        (activityRule.activity.application as PremiseWeatherTestApplication).appComponent.inject(this)

        `when`(mockWeatherProvider.getWeatherBySearch(anyString())).thenReturn(Observable.just(Weather(
                listOf(ConsolidatedWeather("Rainy")),
                "Seattle"
        )))
    }

    @Test
    fun typingAnAddressIsPossible() {
        val testAddress = "123 Street"

        onView(withId(R.id.locationEntry)).perform(typeText(testAddress), closeSoftKeyboard())

        onView(withId(R.id.locationEntry)).check(matches(withText(testAddress)))
    }

    @Test
    fun searchingAnAddressIsPossible() {
        val testAddress = "Seattle"

        onView(withId(R.id.locationEntry)).perform(typeText(testAddress), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(click())

        sleep(100)

        verify(mockWeatherProvider).getWeatherBySearch(testAddress)
    }
}
