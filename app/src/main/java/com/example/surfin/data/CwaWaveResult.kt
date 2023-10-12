package com.example.surfin.data

import com.squareup.moshi.Json

data class CwaWaveResult(
    @Json(name = "Success")
    val success: String,
    @Json(name = "Records")
    val records: Records
)


data class Records(
    @Json(name = "SeaSurfaceObs")
    val seaSurfaceObs: SeaSurfaceObs
)


data class SeaSurfaceObs(
    @Json(name = "Location")
    val location: List<Location>
)

data class Location(
    @Json(name = "StationObsTimes")
    val stationObsTimes: StationObsTimes
)


data class StationObsTimes(
    @Json(name = "StationObsTime")
    val stationObsTime: List<StationObsTime>
)

data class StationObsTime(
    @Json(name = "DateTime")
    val dateTime: String,
    @Json(name = "WeatherElements")
    val weatherElements: WeatherElements
)

data class WeatherElements(
    @Json(name = "WaveHeight")
    val waveHeight: String,
)