package com.jlillioja.premiseweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PremiseWeatherApplication).appComponent.inject(this)
        compositeDisposable = CompositeDisposable()
        weatherListAdapter = WeatherListAdapter(this)
        weatherList.adapter = weatherListAdapter

        submitButton.setOnClickListener {
            weatherProvider
                    .getWeatherBySearch(locationEntry.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->
                        weatherListAdapter.weather = weather
                    }, { error ->
                        log(error.localizedMessage)
                        toast("Could not find weather for that location.")
                        weatherListAdapter.weather = listOf()
                    })
                    .also { compositeDisposable.add(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

}
