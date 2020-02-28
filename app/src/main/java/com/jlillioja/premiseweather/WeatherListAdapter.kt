package com.jlillioja.premiseweather

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import kotlin.math.roundToInt

class WeatherListAdapter(private val activity: Activity) : ArrayAdapter<Weather>(activity, R.layout.weather_list_item) {
    var weather: List<Weather>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return (weather?.size ?: 0) + 1
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return if (position == 0) {
            activity.layoutInflater.inflate(R.layout.weather_list_header, parent, false)
        } else {
            activity.layoutInflater.inflate(R.layout.weather_list_item, parent, false)
                    .apply {
                        weather?.get(position-1)?.let {
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
}