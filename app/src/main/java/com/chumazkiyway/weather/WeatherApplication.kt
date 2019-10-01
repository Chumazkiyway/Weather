package com.chumazkiyway.weather

import android.app.Application
import com.chumazkiyway.weather.utils.di.*

class WeatherApplication : Application() {

    companion object {
        lateinit var weatherComponent : WeatherComponent
    }

    override fun onCreate() {
        super.onCreate()
        weatherComponent = DaggerWeatherComponent.builder()
            .networkModule(NetworkModule(this))
            .locationModule(LocationModule())
            .build()
    }
}