package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


//@Entity(tableName = "user_info", primaryKeys = ["user_id"])
//@Parcelize
//data class User(
//    @ColumnInfo(name = "user_id")
//    val userId: String,
//    @Embedded
//    val activityHistory: ActivityHistory,
//    @Embedded
//    val collection: UserCollection
//) : Parcelable


@Entity(tableName = "user_history", primaryKeys = ["location_title","date"])
@Parcelize
data class UserActivityHistory(
    @ColumnInfo(name = "location_title")
    val locationTitle: String,
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


