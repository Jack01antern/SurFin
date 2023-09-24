package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.example.surfin.data.localsource.SurfinConverters
import kotlinx.parcelize.Parcelize


//@Entity(tableName = "user_collection", primaryKeys = ["lat", "long"])
//@TypeConverters(SurfinConverters::class)
@Parcelize
data class Spots(
//    @ColumnInfo(name = "id")
    val id: String = "",
//    @ColumnInfo(name = "lat")
    val lat: Double = 0.0,
//    @ColumnInfo(name = "long")
    val long: Double = 0.0,
//    @ColumnInfo(name = "tideStationId")
    val tideStationId: String="",
//    @ColumnInfo(name = "tempStationId")
    val tempStationId: String="",
//    @ColumnInfo(name = "wdsdStationId")
    val wdsdStationId: String="",
//    @ColumnInfo(name = "uviStationId")
    val uviStationId: String="",
//    @ColumnInfo(name = "title")
    val title: String="",
//    @ColumnInfo(name = "content")
    val content: String="",
//    @ColumnInfo(name = "photo")
    val photo: List<String> = listOf()
):Parcelable
