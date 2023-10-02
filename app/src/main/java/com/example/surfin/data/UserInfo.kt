package com.example.surfin.data

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.surfin.data.localsource.SurfinConverters
import kotlinx.parcelize.Parcelize


@Entity(tableName = "user_info")
@TypeConverters(SurfinConverters::class)
@Parcelize
data class UserInfo(
    @PrimaryKey(autoGenerate = false)
    val id: Long = 1L,
    @ColumnInfo(name = "selfie")
    val selfie: String,
    @ColumnInfo(name = "name")
    val name: String
) : Parcelable