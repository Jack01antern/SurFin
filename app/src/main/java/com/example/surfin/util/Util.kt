package com.example.surfin.util

import com.example.surfin.SurfinApplication

object Util {
    fun getString(resourceId: Int): String {
        return SurfinApplication.instance.getString(resourceId)
    }

}