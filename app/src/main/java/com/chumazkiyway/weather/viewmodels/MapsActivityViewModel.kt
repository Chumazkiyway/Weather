package com.chumazkiyway.weather.viewmodels

import androidx.lifecycle.ViewModel
import com.chumazkiyway.weather.WeatherApplication
import com.chumazkiyway.weather.models.Location
import com.chumazkiyway.weather.utils.network.Network
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class MapsActivityViewModel : ViewModel() {

    var tmpLatLng = LatLng(0.0, 0.0)

    @Inject
    lateinit var location: Location

    @Inject
    lateinit var network: Network

    init {
        WeatherApplication.weatherComponent.inject(this)
    }

    fun onMapClick (latLng: LatLng?) {
        tmpLatLng = latLng!!
    }
}