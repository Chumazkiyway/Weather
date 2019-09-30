package com.chumazkiyway.weather.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chumazkiyway.weather.models.DayForecast
import com.chumazkiyway.weather.models.Location
import com.chumazkiyway.weather.models.TimeForecast
import com.chumazkiyway.weather.network.Network
import com.chumazkiyway.weather.network.Response
import io.reactivex.disposables.CompositeDisposable
import java.text.FieldPosition
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class MainActivityViewModel(private val locale: Locale) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val network = Network("WYw6I6BXtRZ6FoolWOcbk" , "Frab59acfa8ANWYQ3TDY5BfWo9K2iEsHP7n5d16L")

    var currentPosition = 0

    var location = Location("Zaporizhzhya, ua", 35.18333f, 47.81667f)
    var dailyForecastList = MutableLiveData<List<DayForecast>>()
    var timeForecastList = MutableLiveData<List<TimeForecast>>()

    init {
        compositeDisposable.add(
            network.getDailyForecast(location.lat, location.long)
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

                            val d = DayForecast(dayOfWeek.toString(), date.toString(), period.dateTimeISO!!,
                                period.minTempC!!, period.maxTempC!!, period.maxHumidity!!, period.windDirMax!!,
                                period.windSpeedMaxKPH!!)
                            list.add(d)
                        }
                    }
                    dailyForecastList.value = list
                    getTimeForecast(currentPosition)
                }
        )
    }

    fun getTimeForecast (position: Int) {
        currentPosition = position

        var from = dailyForecastList.value?.get(position)?.dateISO

        //add 1 day(next day) to parameter - "to"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
        val c = Calendar.getInstance()
        c.time = sdf.parse(from)
        c.add(Calendar.DATE, 1)
        val to = sdf.format(c.time)

        if(currentPosition == 0){
            //get current date with hours
            from = Calendar.getInstance().time.toString()
        }

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
                                val t = TimeForecast(time.toString(), period.weather!!, period.maxTempC!! )
                                list.add(t)
                            }
                        }
                        timeForecastList.value = list
                    }
            )
        }
    }

    fun onDestroy() = compositeDisposable.dispose()

}