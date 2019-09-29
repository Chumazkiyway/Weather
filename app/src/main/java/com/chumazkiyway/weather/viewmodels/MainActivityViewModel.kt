package com.chumazkiyway.weather.viewmodels

import androidx.lifecycle.ViewModel
import com.chumazkiyway.weather.models.Location

class MainActivityViewModel : ViewModel() {

    var location = Location("Zaporizhzhya, ua" , 35.18333f, 47.81667f)
}