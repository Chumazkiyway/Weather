package com.chumazkiyway.weather.utils.di

import android.content.Context
import com.chumazkiyway.weather.R
import com.chumazkiyway.weather.utils.network.Network
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {
    @Provides
    @Singleton
    fun getNetwork() : Network = Network(context.getString(R.string.weather_access_id),
                                         context.getString(R.string.weather_secret_key))
}