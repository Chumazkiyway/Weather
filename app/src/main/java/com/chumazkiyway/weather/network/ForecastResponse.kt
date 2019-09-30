package com.chumazkiyway.weather.network

import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("success")
    var success: Boolean? = null
    @SerializedName("response")
    var locPeriods: Array<LocPeriods>? = null
    @SerializedName("error")
    var error: String? = null
}

class LocPeriods {
    @SerializedName("loc")
    var loc: Loc? = null
    @SerializedName("periods")
    var periods: Array<Periods>? = null
}

class Loc {
    @SerializedName("success")
    var long: Float? = null
    @SerializedName("lat")
    var lat: Float? = null
}

class Periods {
    @SerializedName("dateTimeISO")
    var dateTimeISO: String? = null
    @SerializedName("windDirMax")
    var windDirMax: String? = null
    @SerializedName("windSpeedMaxKPH")
    var windSpeedMaxKPH: Int? = null
    @SerializedName("maxTempC")
    var maxTempC: Int? = null
    @SerializedName("maxHumidity")
    var maxHumidity: Int? = null
    @SerializedName("minTempC")
    var minTempC: Int? = null
    @SerializedName("icon")
    var icon: String? = null
}