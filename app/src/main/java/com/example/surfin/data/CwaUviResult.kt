package com.example.surfin.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class CwaUviResult(
    val success: String,
    val records: UviRecords
):Parcelable

@Parcelize
data class UviRecords(
    val weatherElement: UviWeatherElement
):Parcelable

@Parcelize
data class UviWeatherElement(
    val location: List<UviLocation>
):Parcelable


@Parcelize
data class UviLocation(
    val locationCode: String,
    val value: Float
):Parcelable
