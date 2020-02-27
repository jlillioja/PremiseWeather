package com.jlillioja.premiseweather.dagger

import android.app.Application
import com.jlillioja.premiseweather.dagger.DaggerApplicationComponent

open class PremiseWeatherApplication: Application() {
    open val appComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule())
            .build()
}