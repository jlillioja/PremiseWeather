package com.jlillioja.premiseweather

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class TestRunner: AndroidJUnitRunner() {
    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application? {
        return super.newApplication(cl, PremiseWeatherTestApplication::class.java.name, context)
    }
}