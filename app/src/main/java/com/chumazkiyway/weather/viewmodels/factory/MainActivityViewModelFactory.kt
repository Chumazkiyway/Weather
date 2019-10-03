package com.chumazkiyway.weather.viewmodels.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chumazkiyway.weather.viewmodels.MainActivityViewModel
import java.util.*

class MainActivityViewModelFactory(private val locale: Locale,private val context: Context) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainActivityViewModel(locale, context) as T
    }
}