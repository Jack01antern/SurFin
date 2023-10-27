package com.example.surfin.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CwaEarthquakeResult(
        val success: String,
        val records: EarthquakeRecords
):Parcelable


@Parcelize
data class EarthquakeRecords(
        val Earthquake: List<Earthquake>
):Parcelable

@Parcelize
data class Earthquake(
        val EarthquakeNo: Int,
        val ReportColor: String,
        val EarthquakeInfo: EarthquakeInfo,
        val Intensity: EarthquakeIntensity
):Parcelable

@Parcelize
data class EarthquakeInfo(
        val OriginTime: String,
        val FocalDepth: Float,
        val Epicenter: EarthquakeEpicenter,
        val EarthquakeMagnitude: EarthquakeMagnitude
):Parcelable

@Parcelize
data class EarthquakeEpicenter(
        val Location: String,
        val EpicenterLatitude: Float,
        val EpicenterLongitude: Float
):Parcelable

@Parcelize
data class EarthquakeMagnitude(
        val MagnitudeType: String,
        val MagnitudeValue: Float
):Parcelable

@Parcelize
data class EarthquakeIntensity(
        val ShakingArea: List<EarthquakeShakingArea>
):Parcelable

@Parcelize
data class EarthquakeShakingArea(
        val AreaDesc: String,
        val CountyName: String,
        val AreaIntensity: String,
):Parcelable
