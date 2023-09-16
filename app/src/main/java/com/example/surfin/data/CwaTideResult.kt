package com.example.surfin.data

import com.squareup.moshi.Json


data class CwaTideResult(
    @Json(name = "Success") val success: String? = null,
    @Json(name = "Result") val result: TideResult = TideResult(),
    @Json(name = "Records") val records: TideRecords = TideRecords()
)

data class TideResult(
    @Json(name = "ResourceId") val resourceId: String? = null,
    @Json(name = "Fields") val fields: List<TideField> = listOf()
)

data class TideField(
    @Json(name = "Id") val id: String? = null,
    @Json(name = "Type") val type: String? = null
)

data class TideRecords(
    @Json(name = "SeaSurfaceObs") val seaSurfaceObs: TideSeaSurfaceObs = TideSeaSurfaceObs()
)

data class TideSeaSurfaceObs(
    @Json(name = "Location") val location: List<TideLocation> = listOf()
)

data class TideLocation(
    @Json(name = "Station") val station: TideStation = TideStation(),
    @Json(name = "StationObsTimes") val stationObsTimes: TideStationObsTimes = TideStationObsTimes()
)

data class TideStation(
    @Json(name = "StationID") val stationId: String? = null
)

data class TideStationObsTimes(
    @Json(name = "StationObsTime") val stationObsTime: List<TideStationObsTime> = listOf()
)

data class TideStationObsTime(
    @Json(name = "DateTime") val dateTime: String? = null,
    @Json(name = "WeatherElements") val weatherElements: TideWeatherElements = TideWeatherElements()
)

data class TideWeatherElements(
    @Json(name = "TideHeight") val tideHeight: String? = null,
)

//fun CwaTideResult.toA():(StationObsTime,WeatherElements){
//    return (records.seaSurfaceObs.location[0])
//}