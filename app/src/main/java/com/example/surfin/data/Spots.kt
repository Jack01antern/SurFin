package com.example.surfin.data

data class Spots(
    val id: String = "",
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val tideStationId: String="",
    val tempStationId: String="",
    val wdsdStationId: String="",
    val uviStationId: String="",
    val title: String="",
    val content: String="",
    val photo: List<String> = listOf()
)
