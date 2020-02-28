package com.jlillioja.premiseweather

import android.view.View
import android.widget.ListView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import io.reactivex.Observable
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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

        `when`(mockWeatherProvider.getWeatherBySearch(anyString())).thenReturn(Observable.just(listOf(
                Weather(
                        "Somewhere",
                        "Tomorrow",
                        10.0,
                        1000.0,
                        50.0,
                        "Raining",
                        10
                )
        )))
    }

    @Test
    fun typingLocationIsPossible() {
        val testAddress = "Seattle"

        onView(withId(R.id.locationEntry)).perform(typeText(testAddress), closeSoftKeyboard())

        onView(withId(R.id.locationEntry)).check(matches(withText(testAddress)))
    }

    @Test
    fun searchingLocationIsPossible() {
        val testAddress = "Seattle"

        onView(withId(R.id.locationEntry)).perform(typeText(testAddress), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(click())

        sleep(100)

        verify(mockWeatherProvider).getWeatherBySearch(testAddress)
    }

    @Test
    fun searchingLocation_populatesWeather() {
        val testAddress = "Seattle"

        onView(withId(R.id.weatherList)).check(matches(withListSize(1)))

        onView(withId(R.id.locationEntry)).perform(typeText(testAddress), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(click())

        sleep(100)

        onView(withId(R.id.weatherList)).check(matches(withListSize(2)))
    }


    fun withListSize(size: Int): TypeSafeMatcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            override fun matchesSafely(item: View?): Boolean {
                return (item as? ListView)?.count == size
            }

            override fun describeTo(description: Description) {
                description.appendText("ListView should have $size items")
            }

        }
    }
}
