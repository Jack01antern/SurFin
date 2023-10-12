package com.example.surfin.data

import androidx.lifecycle.LiveData
import com.example.surfin.network.SurfinApi
import com.google.android.gms.common.api.internal.ApiKey

interface SurfinRepository {

    //remote api
    suspend fun getCwaTide(apiKey:String,locationName: String): CwaTideResult {
        return SurfinApi.retrofitService.getCwaTide(apiKey,locationName)
    }

    suspend fun getCwaTemp(apiKey:String,locationName: String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaTemp(apiKey,locationName)
    }


    suspend fun getCwaWdsd(apiKey:String,locationName:String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWdsd(apiKey,locationName)
    }

    suspend fun getCwaWeather(apiKey:String,locationName:String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWeather(apiKey,locationName)
    }

    suspend fun getCwaUvi(apiKey:String,locationName:String): CwaUviResult {
        return SurfinApi.retrofitService.getCwaUvi(apiKey,locationName)
    }


//    suspend fun getCwaEarthquake(locationName: String): CwaEarthquakeResult {
//        return SurfinApi.retrofitService.getCwaEarthquake()
//    }

    //firebase
    fun getFirebaseSpotData()

    fun observeFirebaseSpotData()

    fun setFirebaseSpotInfo(spots: Spots)


    //local db
    suspend fun insertHistory(user: UserActivityHistory)

    suspend fun clearHistory()

    fun getAllHistory(): LiveData<List<UserActivityHistory>>

    fun addToCollection(spots: Spots)

    fun getAllCollection(): LiveData<List<Spots>>

    fun removeCollection(lat: Double, longitude: Double)

}