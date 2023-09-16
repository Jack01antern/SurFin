package com.example.surfin.data

import com.squareup.moshi.Json

data class CwaUviResult(
    val success: String,
    val result: UviResult,
    val records: UviRecords
)

data class UviResult(
    @Json(name = "resource_id") val resourceId: String,
    val fields: List<UviField>
)

data class UviField(
    val id: String,
    val type: String
)

data class UviRecords(
    val weatherElement: UviWeatherElement
)

data class UviWeatherElement(
    val elementName: String,
    val time: UviTime
)

data class UviTime(
    @Json(name = "dataTime") val dataTime: String
)

data class LocationData(
    @Json(name = "locationCode") val locationCode: String,
    @Json(name = "value") val value: Float
)
