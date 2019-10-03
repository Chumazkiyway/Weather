package com.chumazkiyway.weather.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumazkiyway.weather.WeatherApplication
import com.chumazkiyway.weather.models.Location
import com.chumazkiyway.weather.utils.network.Network
import javax.inject.Inject
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient


class SearchActivityViewModel : ViewModel() {

    val predictions = MutableLiveData<List<String>>()
    val isLoading = MutableLiveData<Boolean>()

    @Inject
    lateinit var location: Location

    @Inject
    lateinit var network: Network

    init {
        WeatherApplication.weatherComponent.inject(this)
    }

    fun getLocations(placesClient: PlacesClient, query : String) {
        val token = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            .setTypeFilter(TypeFilter.CITIES)
            .setSessionToken(token)
            .setQuery(query)
            .build()

        isLoading.value = true

        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            val list = arrayListOf<String>()
            for (prediction in response.autocompletePredictions) {
                Log.i("PLACES SDK", prediction.getFullText(null).toString())
                list.add(prediction.getFullText(null).toString())
            }
            isLoading.value = false
            predictions.value = list
        }.addOnFailureListener { exception ->
            if (exception is ApiException) {
                val apiException = exception as ApiException
                Log.e("PLACES SDK", "Place not found: " + apiException.statusCode)
            }
            isLoading.value = false
        }
    }
}