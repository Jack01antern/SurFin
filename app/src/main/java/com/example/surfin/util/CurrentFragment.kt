package com.example.surfin.util

import com.example.surfin.R
import com.example.surfin.util.Util.getString

enum class CurrentFragment(val value: String) {
    HOME(getString(R.string.weather)),
    WEATHER(getString(R.string.weather)),
    EXPLORE(getString(R.string.explore)),
    EMERGENCY(getString(R.string.emergency)),
    ACCOUNT(getString(R.string.account)),
    HISTORY(getString(R.string.activity_history)),
    COLLECTION(getString(R.string.collection)),
}