package com.example.surfin.data

import com.squareup.moshi.Json

data class CwaTempResult(
   val success: String?=null,
    val result: TempWeatherResult,
    val records: TempWeatherRecords
)

data class TempWeatherResult(
    @Json(name = "resource_id")val resourceId: String,
    val fields: List<TempWeatherField>
)

data class TempWeatherField(
    val id: String,
    val type: String
)

data class TempWeatherRecords(
    val location: List<TempWeatherLocation>
)

data class TempWeatherLocation(
    val lat: String,
    val lon: String,
    val locationName: String,
    val stationId: String,
    val time: TempWeatherObsTime,
    val weatherElement: List<TempWeatherElement>,
    val parameter: List<TempWeatherParameter>
)

data class TempWeatherObsTime(
    val obsTime: String
)

data class TempWeatherElement(
    val elementName: String,
    val elementValue: String
)

data class TempWeatherParameter(
    val parameterName: String,
    val parameterValue: String
)
