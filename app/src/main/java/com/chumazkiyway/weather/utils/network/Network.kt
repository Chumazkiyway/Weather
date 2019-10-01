package com.chumazkiyway.weather.utils.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Network (private val clientId: String, private val clientSecret: String) {

    private val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(WEATHER_ADDRESS )
        .client(httpClient)
        .build()

    fun getDailyForecast( long: Double, lat: Double) : Observable<Response> {
        val networkApi = retrofit.create(WeatherApi::class.java)
        return networkApi.getDailyForecast(long, lat, clientId, clientSecret).observeOn(AndroidSchedulers.mainThread())
    }

    fun getTimeForecast( long: Double, lat: Double, from: String, to: String) : Observable<Response>{
        val networkApi = retrofit.create(WeatherApi::class.java)
        return networkApi.getTimeForecast(long, lat, from, to, clientId, clientSecret).observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        const val WEATHER_ADDRESS = "https://api.aerisapi.com/"
    }
}