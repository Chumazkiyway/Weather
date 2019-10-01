package com.chumazkiyway.weather.utils.di

import com.chumazkiyway.weather.models.Location
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {
    @Provides
    @Singleton
    fun getLocation() : Location = Location("Zaporizhzhia", 35.18333, 47.81667)
}