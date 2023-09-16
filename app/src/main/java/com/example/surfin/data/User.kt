package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize


@Entity(tableName = "user_info", primaryKeys = ["user_id"])
@TypeConverters
@Parcelize
data class User(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @Embedded
    val activityHistory: ActivityHistory,
    @Embedded
    val collection: UserCollection
) : Parcelable

@Parcelize
data class ActivityHistory(
    @ColumnInfo(name = "location_title")
    val locationTitle: String,
    @ColumnInfo(name = "location")
    val location: String,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "heart_rate")
    val heartRate: String,
    @ColumnInfo(name = "start_time")
    val startTime: Long,
    @ColumnInfo(name = "end_time")
    val endTime: Long,
    @ColumnInfo(name = "calories")
    val calories: String,
    @ColumnInfo(name = "photo")
    val photo: String
) : Parcelable

@Parcelize
data class UserCollection(
    @ColumnInfo(name = "location_id")
    val locationId: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double
) : Parcelable