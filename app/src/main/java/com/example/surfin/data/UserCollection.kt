package com.example.surfin.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_collection", primaryKeys = ["latitude","longitude"])
@Parcelize
data class UserCollection(
    @ColumnInfo(name = "location_id")
    val locationId: String,
    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double
) : Parcelable