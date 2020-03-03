package com.jlillioja.premiseweather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
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

    private var mapFragment: SupportMapFragment? = null
    private var currentSearch: String = ""
    private var currentLocation: Location? = null
    private var searchingByLocation = false

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
            searchingByLocation = false
            fetchWeather()
        }

        locateButton.setOnClickListener {
            searchingByLocation = true
            try {
                fetchLocation()
            } catch (e: SecurityException) {
                Log.e("Exception: %s", e.message)
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchWeather()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun fetchLocation() {
        if (mLocationPermissionGranted) {
            LocationServices.getFusedLocationProviderClient(this)
                    .lastLocation
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful && it.result != null) {
                            currentLocation = it.result
                            setMapLocation(it.result!!.latLong())
                            fetchWeather()
                        }
                    }
        } else {
            getLocationPermission()
        }
    }

    var mLocationPermissionGranted = false
    private val requestCode = 1
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
            fetchLocation()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), requestCode)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (grantResults.getOrNull(0) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true
            fetchLocation()
        } else {
            toast("Location permissions must be granted to use this function.")
        }
    }

    private fun fetchWeather() {
        swipeRefreshLayout.isRefreshing = true
        val weatherObservable = if (searchingByLocation && currentLocation != null) weatherProvider.getWeatherByLocation(currentLocation!!) else weatherProvider.getWeatherBySearch(currentSearch)
        weatherObservable.observeOn(AndroidSchedulers.mainThread())
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

    private fun setMapLocation(location: LatLng, locationTitle: String? = null) {
        mapFragment?.getMapAsync { map ->
            map.clear()
            map.addMarker(MarkerOptions().position(location).title(locationTitle))
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, mapZoomLevel))
        }
    }

    private fun Location.latLong() = LatLng(this.latitude, this.longitude)
}
