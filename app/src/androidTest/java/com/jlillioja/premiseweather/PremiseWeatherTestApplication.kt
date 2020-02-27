package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.dagger.PremiseWeatherApplication

class PremiseWeatherTestApplication: PremiseWeatherApplication() {
    override val appComponent = DaggerTestApplicationComponent.builder()
            .testApplicationModule(TestApplicationModule())
            .build()
}