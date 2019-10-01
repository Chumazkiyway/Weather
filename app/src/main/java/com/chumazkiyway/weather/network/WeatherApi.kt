package com.chumazkiyway.weather.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecasts/{lat},{long}?" +
            "&format=json" +
            "&filter=day" +
            "&limit=7" +
            "&fields=periods.icon,periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity," +
            "periods.windSpeedMaxKPH,periods.windDirMax")
    fun getDailyForecast(
        @Path("lat") lat: Double,
        @Path("long") long: Double,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>

    @GET("forecasts/{lat},{long}?" +
            "&format=json" +
            "&filter=1hr" +
            "&limit=168" +
            "&fields=periods.icon,periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity," +
            "periods.windSpeedMaxKPH,periods.windDirMax")
    fun getTimeForecast(
        @Path("lat") lat: Double,
        @Path("long") long: Double,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>
}