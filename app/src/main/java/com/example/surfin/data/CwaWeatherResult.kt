package com.example.surfin.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CwaWeatherResult(
    val success: String? = null,
    val records: TempWeatherRecords
) : Parcelable


@Parcelize
data class TempWeatherRecords(
    @Json(name = "Station")
    val station: List<TempWeatherStation>
) : Parcelable

@Parcelize
data class TempWeatherStation(
    @Json(name = "WeatherElement")
    val weatherElement: TempWeatherElement
) : Parcelable

@Parcelize
data class TempWeatherObsTime(
    val obsTime: String
) : Parcelable

@Parcelize
data class TempWeatherElement(
    @Json(name = "Weather")
    val weather: String,
    @Json(name = "WindSpeed")
    val windSpeed: Float,
    @Json(name = "AirTemperature")
    val airTemperature: Float,
) : Parcelable
