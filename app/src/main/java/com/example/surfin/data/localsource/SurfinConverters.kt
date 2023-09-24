package com.example.surfin.data.localsource

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class SurfinConverters {

    @TypeConverter
    fun convertListToJson(list: List<String>?): String? {
        list?.let {
            return Moshi.Builder().build().adapter<List<String>>(List::class.java).toJson(list)
        }
        return null
    }

    @TypeConverter
    fun convertJsonToList(json: String?): List<String>? {
        json?.let {
            val type = Types.newParameterizedType(List::class.java, String::class.java)
            val adapter: JsonAdapter<List<String>> = Moshi.Builder().build().adapter(type)
            return adapter.fromJson(it)
        }
        return null
    }
}