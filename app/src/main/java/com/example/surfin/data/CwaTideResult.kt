package com.example.surfin.data

import com.squareup.moshi.Json


data class CwaTideResult(
    val success: String,
    val records: TideRecords
)


data class TideRecords(
    @Json(name = "TideForecasts") val tideForecasts: List<TideForecast>
)

data class TideForecast(
    @Json(name = "Location") val location: TideLocation,
)

data class TideLocation(
    @Json(name = "LocationId") val locationId: String,
    @Json(name = "LocationName") val locationName: String,
    @Json(name = "TimePeriods") val timePeriods: TimePeriods
)

data class TimePeriods(
    @Json(name = "Daily")val daily: List<TideDaily>
)

data class TideDaily(
    @Json(name = "Date") val date: String,
    @Json(name = "Time")val tideTime: List<TideTime>
)

data class TideTime(
    @Json(name = "DateTime") val dateTime: String,
    @Json(name = "TideHeights")val tideHeights: TideHeightData
)

data class TideHeightData(
    @Json(name = "AboveTWVD") val aboveTWVD: String
)
