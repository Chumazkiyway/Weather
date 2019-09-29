package com.chumazkiyway.weather.network

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecasts/{long},{lat}?" +
            "&format=json" +
            "&filter=day" +
            "&limit=7" +
            "&fields=periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity,periods.windSpeedMaxKPH,periods.windDirMax,periods.weather")
    fun getDailyForecast(
        @Path("long") long: Float,
        @Path("lat") lat: Float,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>

    @GET("forecasts/{long},{lat}?" +
            "&format=json" +
            "&filter=1hr" +
            "&limit=24" +
            "&fields=periods.dateTimeISO,periods.maxTempC,loc,periods.minTempC,periods.maxHumidity,periods.windSpeedMaxKPH,periods.windDirMax,periods.weather")
    fun getTimeForecast(
        @Path("long") long: Float,
        @Path("lat") lat: Float,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String ): Observable<Response>
}