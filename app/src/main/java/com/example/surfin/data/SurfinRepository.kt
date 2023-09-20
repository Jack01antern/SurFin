package com.example.surfin.data

import androidx.lifecycle.LiveData
import com.example.surfin.network.SurfinApi

interface SurfinRepository {

    //remote api
    suspend fun getCwaTide(): CwaTideResult {
        return SurfinApi.retrofitService.getCwaTide()
    }

    suspend fun getCwaTemp(): CwaTempResult {
        return SurfinApi.retrofitService.getCwaTemp()
    }


    suspend fun getCwaWdsd(locationName:String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWdsd()
    }


    suspend fun getCwaUvi(): CwaUviResult {
        return SurfinApi.retrofitService.getCwaUvi()
    }


    suspend fun getCwaEarthquake(): CwaEarthquakeResult {
        return SurfinApi.retrofitService.getCwaEarthquake()
    }

    //firebase
    fun getFirebaseSpotData()

    fun observeFirebaseSpotData()

    fun setFirebaseSpotInfo(spots: Spots)


    //local db
    suspend fun insert(user: UserActivityHistory)

    suspend fun clear()

    fun getAllHistory(): LiveData<List<UserActivityHistory>>
}