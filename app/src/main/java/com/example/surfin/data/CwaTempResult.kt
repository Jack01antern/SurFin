package com.example.surfin.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CwaTempResult(
   val success: String?=null,
    val records: TempWeatherRecords
):Parcelable


@Parcelize
data class TempWeatherRecords(
    val location: List<TempWeatherLocation>
):Parcelable

@Parcelize
data class TempWeatherLocation(
    val locationName: String,
    val stationId: String,
    val time: TempWeatherObsTime,
    val weatherElement: List<TempWeatherElement>,
    val parameter: List<TempWeatherParameter>
):Parcelable

@Parcelize
data class TempWeatherObsTime(
    val obsTime: String
):Parcelable

@Parcelize
data class TempWeatherElement(
    val elementName: String,
    val elementValue: String
):Parcelable

@Parcelize
data class TempWeatherParameter(
    val parameterValue: String
):Parcelable
