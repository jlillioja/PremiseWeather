package com.jlillioja.premiseweather

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jlillioja.premiseweather.dagger.PremiseWeatherApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherProvider: WeatherProvider

    private lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as PremiseWeatherApplication).appComponent.inject(this)
        compositeDisposable = CompositeDisposable()

        submitButton.setOnClickListener {
            weatherProvider
                    .getWeatherBySearch(locationEntry.text.toString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ weather ->
                        toast("The weather in ${weather.title} is ${weather.consolidated_weather.firstOrNull()?.weather_state_name ?: "missing!"}")
                    }, { error ->
                        log(error.localizedMessage)
                    })
                    .also { compositeDisposable.add(it) }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    private fun log(text: String) = Log.d("MainActivity", text)
}
