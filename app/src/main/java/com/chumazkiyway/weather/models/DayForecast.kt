package com.chumazkiyway.weather.models

data class DayForecast(val date: String, val minT: Int, val maxT: Int, val humidity: Int, val windDirection: String,
                       val windSpeed: Int)