package com.chumazkiyway.weather.utils.di

import com.chumazkiyway.weather.viewmodels.MainActivityViewModel
import com.chumazkiyway.weather.viewmodels.MapsActivityViewModel
import com.chumazkiyway.weather.viewmodels.SearchActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LocationModule::class])
interface WeatherComponent {
    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: MapsActivityViewModel)
    fun inject(viewModel: SearchActivityViewModel)
}