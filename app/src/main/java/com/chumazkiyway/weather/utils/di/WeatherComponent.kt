package com.chumazkiyway.weather.utils.di

import com.chumazkiyway.weather.viewmodels.MainActivityViewModel
import com.chumazkiyway.weather.viewmodels.MapsActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocationModule::class])
interface WeatherComponent {
    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: MapsActivityViewModel)
}