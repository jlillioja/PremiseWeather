package com.jlillioja.premiseweather

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlin.math.roundToInt

class WeatherListAdapter(private val activity: Activity) : ArrayAdapter<WeatherList>(activity, R.layout.weather_list_item) {
    var weather: WeatherList? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return weather?.forecast?.size ?: 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return (convertView ?: activity.layoutInflater.inflate(R.layout.weather_list_item, parent, false))
                .apply {
                    weather?.forecast?.get(position)?.let {
                        findViewById<TextView>(R.id.day).text = it.day
                        findViewById<TextView>(R.id.temperature).text = it.temperature.roundToInt().toString()
                        findViewById<TextView>(R.id.pressure).text = it.pressure.roundToInt().toString()
                        findViewById<TextView>(R.id.humidity).text = it.humidity.roundToInt().toString()
                        findViewById<TextView>(R.id.conditions).text = it.conditions
                        findViewById<TextView>(R.id.likelihood).text = it.likelihood.toString()
                    }
                }
    }
}
