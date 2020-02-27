package com.jlillioja.premiseweather.dagger

import com.jlillioja.premiseweather.MainActivity
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}