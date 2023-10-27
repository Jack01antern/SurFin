package com.example.surfin.util

import com.example.surfin.R
import com.example.surfin.util.Util.getString

enum class WeatherValue(val descriptionResId: Int) {
    SUNNY(R.string.sunny),
    SUNNY_CLOUD(R.string.sunny_cloud),
    CLOUDY(R.string.cloudy),
    CLOUDY_RAIN(R.string.cloudy_rain),
    RAINY(R.string.rainy)
}