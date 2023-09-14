package com.example.surfin.data



data class CwaTideResult(
    val dateTime: String = "",
    val weatherElements:WeatherElements,
    )

data class WeatherElements(
    val tideHeight:String = ""
)