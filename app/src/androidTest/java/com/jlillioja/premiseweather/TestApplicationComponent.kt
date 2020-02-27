package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.dagger.ApplicationComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApplicationModule::class])
interface TestApplicationComponent: ApplicationComponent {
    fun inject(test: MainActivityInstrumentedTest)
}