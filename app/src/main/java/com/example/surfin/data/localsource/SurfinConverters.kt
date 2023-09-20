package com.example.surfin.data.localsource

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi

class SurfinConverters {

    @TypeConverter
    fun convertListToJson(list: List<String>?): String? {
        list?.let {
            return Moshi.Builder().build().adapter<List<String>>(List::class.java).toJson(list)
        }
        return null
    }
}