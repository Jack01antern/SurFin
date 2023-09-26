package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.surfin.data.localsource.SurfinConverters
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize


@Entity(tableName = "user_collection", primaryKeys = ["lat", "longitude"])
@TypeConverters(SurfinConverters::class)
@Parcelize
data class Spots(

    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "lat")
    var lat: Double = 0.0,

    @ColumnInfo(name = "longitude")
    var longitude: Double = 0.0,

    @Json(name = "tideStationId")
    @ColumnInfo(name = "tide_station_id")
    var tideStationId: String = "",

    @Json(name = "tempStationId")
    @ColumnInfo(name = "temp_station_id")
    var tempStationId: String = "",

    @Json(name = "StationId")
    @ColumnInfo(name = "weather_station_id")
    var weatherStationId: String = "",

    @Json(name = "wdsdStationId")
    @ColumnInfo(name = "wdsd_station_id")
    var wdsdStationId: String = "",

    @Json(name = "uviStationId")
    @ColumnInfo(name = "uvi_station_id")
    var uviStationId: String = "",

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "content")
    var content: String = "",

    @ColumnInfo(name = "photo")
    var photo: List<String> = listOf()
) : Parcelable
