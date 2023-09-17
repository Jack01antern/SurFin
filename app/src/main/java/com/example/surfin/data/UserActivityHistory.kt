package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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


@Entity(tableName = "user_history")
@Parcelize
data class UserActivityHistory(
    @PrimaryKey(autoGenerate = true)
    val activityId:Long,
    @ColumnInfo(name = "location_title")
    val locationTitle: String,
    @ColumnInfo(name = "date")
    val date: Long,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "heart_rate")
    val heartRate: String,
    @ColumnInfo(name = "time_duration")
    val timeDuration: String,
    @ColumnInfo(name = "calories")
    val calories: String,
    @ColumnInfo(name = "photo")
    val photo: String
) : Parcelable


