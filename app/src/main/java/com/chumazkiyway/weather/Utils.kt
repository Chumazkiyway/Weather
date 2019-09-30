package com.chumazkiyway.weather

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*


fun getFromTo(fromStr: String, position: Int, locale: Locale) : Pair<String?, String?> {
    var from = fromStr

    //add 1 day(next day) to parameter - "to"
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
    val c = Calendar.getInstance()
    c.time = sdf.parse(from)
    c.add(Calendar.DATE, 1)
    val to = sdf.format(c.time)

    if(position == 0){
        //get current date with hours
        from = Calendar.getInstance().time.toString()
    }
    return Pair(from, to)
}

fun getWindDirIcon(winDir: String) : Int{

    val dir = if(winDir.length > 2) {
        //to get last 2 characters
        winDir.substring(winDir.length-2)
    } else {
        winDir
    }

    return when(dir) {
        "N" -> R.drawable.ic_icon_wind_n
        "E" -> R.drawable.ic_icon_wind_e
        "S" -> R.drawable.ic_icon_wind_s
        "W" -> R.drawable.ic_icon_wind_w
        "NE", "EN" -> R.drawable.ic_icon_wind_ne
        "SE", "ES" -> R.drawable.ic_icon_wind_se
        "SW", "WS" -> R.drawable.ic_icon_wind_ws
        else -> R.drawable.ic_icon_wind_wn //NW, WN
    }
}

fun getWeatherIcon(icon: String) : Int {
    return  if(icon.substring(icon.length-5).first() != 'n' ) { //if day's icon
        when(icon) {
            "clear.png","fair.png", "hazy.png", "hot.png", "sunny.png", "sunnyw.png" -> R.drawable.ic_white_day_bright
            "drizzle.png", "drizzlef.png", "freezingrain.png", "mcloudyr.png", "mcloudyrw.png", "mcloudys.png",
            "pcloudyr.png", "pcloudyrw.png", "pcloudyt.png", "rain.png", "rainandsnow.png", "showers.png" ,
            "showersw.png"-> R.drawable.ic_white_day_rain
            "blowingsnow.png", "blizzard.png", "mcloudyt.png", "rainw.png", "snowtorain.png", "tstorm.png",
            "wintrymix.png"-> R.drawable.ic_white_day_shower
            "tstorms.png", "tstormsw.png" ->R.drawable.ic_white_day_thunder
            else -> R.drawable.ic_white_day_cloudy
        }
    } else {
        when(icon) {
            "clearn.png","fairn.png", "hazyn.png", "sunnyn.png", "sunnywn.png" -> R.drawable.ic_white_night_bright
            "drizzlen.png", "drizzlefn.png", "freezingrainn.png", "mcloudyrn.png", "mcloudyrwn.png", "mcloudysn.png",
            "pcloudyrn.png", "pcloudyrwn.png", "pcloudytn.png", "rainn.png", "rainandsnown.png", "showersn.png" ,
            "showerswn.png"-> R.drawable.ic_white_night_rain
            "blowingsnown.png", "blizzardn.png", "mcloudytn.png", "rainwn.png", "snowtorainn.png", "tstormn.png",
            "wintrymixn.png"-> R.drawable.ic_white_night_shower
            "tstormsn.png", "tstormswn.png" -> R.drawable.ic_white_night_thunder
            else -> R.drawable.ic_white_night_cloudy
        }
    }
}