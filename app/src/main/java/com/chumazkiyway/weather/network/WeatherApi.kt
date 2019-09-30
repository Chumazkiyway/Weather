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
            "&fields=periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity,periods.windSpeedMaxKPH,periods.windDirMax,periods.weather")
    fun getDailyForecast(
        @Path("lat") lat: Float,
        @Path("long") long: Float,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>

    @GET("forecasts/{lat},{long}?" +
            "&format=json" +
            "&filter=1hr" +
            "&limit=168" +
            "&fields=periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity,periods.windSpeedMaxKPH,periods.windDirMax,periods.weather")
    fun getTimeForecast(
        @Path("lat") lat: Float,
        @Path("long") long: Float,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>
}