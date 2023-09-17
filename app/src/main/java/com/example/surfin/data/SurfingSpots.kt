package com.example.surfin.data

data class SurfingSpots(
    val id: String,
    val long: Double,
    val lat: Double,
    val tempStationId:String,
    val uviStationId:String,
    val tideStationId:String
)