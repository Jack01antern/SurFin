package com.example.surfin.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CwaTideResult(
    @Json(name = "Success") val success: String? = null,
    @Json(name = "Records") val records: TideRecords = TideRecords()
):Parcelable


@Parcelize
data class TideRecords(
    @Json(name = "SeaSurfaceObs") val seaSurfaceObs: TideSeaSurfaceObs = TideSeaSurfaceObs()
):Parcelable

@Parcelize
data class TideSeaSurfaceObs(
    @Json(name = "Location") val location: List<TideLocation> = listOf()
):Parcelable

@Parcelize
data class TideLocation(
    @Json(name = "Station") val station: TideStation = TideStation(),
    @Json(name = "StationObsTimes") val stationObsTimes: TideStationObsTimes = TideStationObsTimes()
):Parcelable

@Parcelize
data class TideStation(
    @Json(name = "StationID") val stationId: String? = null
):Parcelable

@Parcelize
data class TideStationObsTimes(
    @Json(name = "StationObsTime") val stationObsTime: List<TideStationObsTime> = listOf()
):Parcelable

@Parcelize
data class TideStationObsTime(
    @Json(name = "DateTime") val dateTime: String? = null,
    @Json(name = "WeatherElements") val weatherElements: TideWeatherElements = TideWeatherElements()
):Parcelable

@Parcelize
data class TideWeatherElements(
    @Json(name = "TideHeight") val tideHeight: String? = null,
):Parcelable

//fun CwaTideResult.toA():(StationObsTime,WeatherElements){
//    return (records.seaSurfaceObs.location[0])
//}