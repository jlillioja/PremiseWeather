package com.jlillioja.premiseweather

import com.jlillioja.premiseweather.network.MetaWeatherInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import org.mockito.Mockito.*

@Module
class TestApplicationModule {

    @Provides
    fun provideMetaWeatherInterface(): MetaWeatherInterface {
        return mock(MetaWeatherInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesMockWeatherProvider(): WeatherProvider {
        return mock(WeatherProvider::class.java)
    }

}