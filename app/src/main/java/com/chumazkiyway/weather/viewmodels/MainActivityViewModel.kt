package com.chumazkiyway.weather.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumazkiyway.weather.getFromTo
import com.chumazkiyway.weather.getWeatherIcon
import com.chumazkiyway.weather.getWindDirIcon
import com.chumazkiyway.weather.models.DayForecast
import com.chumazkiyway.weather.models.Location
import com.chumazkiyway.weather.models.TimeForecast
import com.chumazkiyway.weather.network.Network
import com.chumazkiyway.weather.network.Response
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import android.location.Geocoder
import android.content.Context
import io.reactivex.Single
import java.io.IOException


class MainActivityViewModel(private val locale: Locale, private val context: Context) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val network = Network("WYw6I6BXtRZ6FoolWOcbk" , "Frab59acfa8ANWYQ3TDY5BfWo9K2iEsHP7n5d16L")
    private val location = Location("Zaporizhzhia", 35.18333, 47.81667)
    private var currentPosition = 0

    val dailyForecastList = MutableLiveData<List<DayForecast>>()
    val timeForecastList = MutableLiveData<List<TimeForecast>>()
    val selectedDay = MutableLiveData<DayForecast>()
    val place = MutableLiveData<String>()

    fun getTimeForecast (position: Int) {
        currentPosition = position
        selectedDay.value = dailyForecastList.value?.get(currentPosition)

        val (from, to) = getFromTo(dailyForecastList.value?.get(currentPosition)?.dateISO!!,
                                                    currentPosition, locale )

        if(from != null && to != null){
            compositeDisposable.add(
                network.getTimeForecast(location.lat, location.long, from, to)
                    .onErrorReturn {
                        Log.d("NETWORK_ERROR", it.message)
                        Response()
                    }
                    .subscribe {
                        val list = arrayListOf<TimeForecast>()
                        it.locPeriods?.forEach {
                            it.periods?.forEach { period ->
                                val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).parse(period.dateTimeISO)
                                val time = SimpleDateFormat("HH", locale).format(df)
                                val weather = getWeatherIcon(period.icon!!)
                                val t = TimeForecast(time.toString(), weather, period.maxTempC!! )
                                list.add(t)
                            }
                        }
                        timeForecastList.value = list
                    }
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(placesClient: PlacesClient) {
        val placeFields = Arrays.asList(Place.Field.LAT_LNG)
        val request = FindCurrentPlaceRequest.newInstance(placeFields)
        val placeResponse = placesClient.findCurrentPlace(request)
        placeResponse.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val response = task.result
                val latLng = response!!.placeLikelihoods.first().place.latLng
                location.lat = latLng!!.latitude
                location.long = latLng.longitude
                compositeDisposable.add(getDayForecast())
                compositeDisposable.add(getCityName())
            } else {
                val exception = task.exception
                if (exception is ApiException) {
                    Log.e("PLACES SDK", "Place not found: " + exception.statusCode)
                }
            }
        }
    }

    fun onDestroy() = compositeDisposable.dispose()

    private fun getDayForecast() = network.getDailyForecast(location.lat, location.long)
        .onErrorReturn {
            Log.d("NETWORK_ERROR", it.message)
            Response()
        }
        .subscribe {
            val list = arrayListOf<DayForecast>()

            it.locPeriods?.forEach{
                it.periods?.forEach { period ->
                    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale).parse(period.dateTimeISO)
                    val dayOfWeek = SimpleDateFormat("EEE", locale).format(df)
                    val date = SimpleDateFormat("EEE, dd MMMMMM", locale).format(df)
                    val windDir = getWindDirIcon(period.windDirMax!!)
                    val weather = getWeatherIcon(period.icon!!)

                    val d = DayForecast(dayOfWeek.toString(), date.toString(), period.dateTimeISO!!,
                        period.minTempC!!, period.maxTempC!!, period.maxHumidity!!, windDir,
                        period.windSpeedMaxKPH!!, weather)
                    list.add(d)
                }
            }
            dailyForecastList.value = list
            selectedDay.value = dailyForecastList.value?.get(currentPosition)
            getTimeForecast(currentPosition)
        }

    private fun getCityName() = Single.create<String>{
            val coder = Geocoder(context, locale)
            try {
                val results = coder.getFromLocation(location.lat, location.long, 1)
                it.onSuccess(results.first().locality)
            } catch (e: IOException) {
                Log.e("GEOCODER ERROR:", e.message)
                it.onError(e)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { res ->
                location.cityName = res
                place.value = res
            }


}
