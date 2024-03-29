package com.chumazkiyway.weather.models

data class DayForecast(val dayOfWeek: String, val date: String, val dateISO: String, val minT: Int, val maxT: Int,
                       val humidity: Int, val windDirection: Int, val windSpeed: Int, val weatherIcon: Int)