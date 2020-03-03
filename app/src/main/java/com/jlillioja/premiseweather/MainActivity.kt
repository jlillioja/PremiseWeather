package com.jlillioja.premiseweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jlillioja.premiseweather.dagger.PremiseWeatherApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

open class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherProvider: WeatherProvider

    private lateinit var weatherListAdapter: WeatherListAdapter
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var mapFragment: SupportMapFragment
    private var currentSearch: String = ""

    private val mapZoomLevel = 8F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PremiseWeatherApplication).appComponent.inject(this)
        compositeDisposable = CompositeDisposable()
        weatherListAdapter = WeatherListAdapter(this)
        weatherList.adapter = weatherListAdapter

        mapFragment = map as SupportMapFragment

        submitButton.setOnClickListener {
            currentSearch = locationEntry.text.toString()
            fetchWeather()
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchWeather()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun fetchWeather() {
        weatherProvider
                .getWeatherBySearch(currentSearch)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ weather ->
                    weatherListAdapter.weather = weather
                    setMapLocation(weather.location, weather.locationTitle)
                    swipeRefreshLayout.isRefreshing = false
                }, { error ->
                    log(error.localizedMessage)
                    toast("Could not find weather for that location.")
                    weatherListAdapter.weather = null
                    swipeRefreshLayout.isRefreshing = false
                })
                .also { compositeDisposable.add(it) }
    }

    private fun setMapLocation(location: LatLng, locationTitle: String) {
        mapFragment.getMapAsync { map ->
            map.clear()
            map.addMarker(MarkerOptions().position(location).title(locationTitle))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, mapZoomLevel))
        }
    }
}
