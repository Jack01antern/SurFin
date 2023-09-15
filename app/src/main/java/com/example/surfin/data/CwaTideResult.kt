package com.example.surfin.data

import com.squareup.moshi.Json


data class CwaTideResult(
    @Json(name = "Success") val success: String = "",
    @Json(name = "Result") val result: Result = Result(),
    @Json(name = "Records") val records: Records = Records()
)

data class Result(
    @Json(name = "ResourceId") val resourceId: String = "",
    @Json(name = "Fields") val fields: List<Field> = listOf()
)

data class Field(
    @Json(name = "Id") val id: String = "",
    @Json(name = "Type") val type: String = ""
)

data class Records(
    @Json(name = "SeaSurfaceObs") val seaSurfaceObs: SeaSurfaceObs = SeaSurfaceObs()
)

data class SeaSurfaceObs(
    @Json(name = "Location") val location: List<Location> = listOf()
)

data class Location(
    @Json(name = "Station") val station: Station = Station(),
    @Json(name = "StationObsTimes") val stationObsTimes: StationObsTimes = StationObsTimes()
)

data class Station(
    @Json(name = "StationID") val stationId: String = ""
)

data class StationObsTimes(
    @Json(name = "StationObsTime") val stationObsTime: List<StationObsTime> = listOf()
)

data class StationObsTime(
    @Json(name = "DateTime") val dateTime: String = "",
    @Json(name = "WeatherElements") val weatherElements: WeatherElements = WeatherElements()
)

data class WeatherElements(
    @Json(name = "TideHeight") val tideHeight: String = ""
)

//fun CwaTideResult.toA():(StationObsTime,WeatherElements){
//    return (records.seaSurfaceObs.location[0])
//}