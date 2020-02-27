package com.jlillioja.premiseweather.dagger

import com.jlillioja.premiseweather.network.MetaWeatherInterface
import com.jlillioja.premiseweather.WeatherProvider
import com.jlillioja.premiseweather.WeatherProviderImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApplicationModule {

    @Provides
    open fun provideMetaWeatherInterface(): MetaWeatherInterface {
        return Retrofit.Builder()
                .baseUrl("https://www.metaweather.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MetaWeatherInterface::class.java)
    }

    @Provides
    open fun providesWeatherProvider(metaWeatherInterface: MetaWeatherInterface): WeatherProvider {
        return WeatherProviderImpl(metaWeatherInterface)
    }

}