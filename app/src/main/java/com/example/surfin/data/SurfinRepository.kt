package com.example.surfin.data

import androidx.lifecycle.LiveData
import com.example.surfin.network.SurfinApi

interface SurfinRepository {

    //remote api
    suspend fun getCwaTide(apiKey: String, locationName: String): CwaTideResult {
        return SurfinApi.retrofitService.getCwaTide(apiKey, locationName)
    }

    suspend fun getCwaTemp(apiKey: String, locationName: String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaTemp(apiKey, locationName)
    }


    suspend fun getCwaWdsd(apiKey: String, locationName: String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWdsd(apiKey, locationName)
    }

    suspend fun getCwaWeather(apiKey: String, locationName: String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWeather(apiKey, locationName)
    }

    suspend fun getCwaUvi(apiKey: String, locationName: String): CwaUviResult {
        return SurfinApi.retrofitService.getCwaUvi(apiKey, locationName)
    }


//    suspend fun getCwaEarthquake(locationName: String): CwaEarthquakeResult {
//        return SurfinApi.retrofitService.getCwaEarthquake()
//    }

    //firebase
    fun getFirebaseSpotData()

    fun observeFirebaseSpotData()

    fun setFirebaseSpotInfo(spots: Spots)


    //local db history
    suspend fun insertHistory(user: UserActivityHistory)


    suspend fun updateHistory(user: UserActivityHistory)

    suspend fun removeFromHistory(activityId: Long)

    suspend fun clearHistory()

    fun getAllHistory(): LiveData<List<UserActivityHistory>>


    //local db collection
    suspend fun addToCollection(spots: Spots)

    fun getAllCollectionLiveData(): LiveData<List<Spots>>

    suspend fun getAllCollection(): List<Spots>


    suspend fun removeCollection(lat: Double, longitude: Double)


    //local db user info

    suspend fun updateUserInfo(userInfo: UserInfo)

    fun getUserInfo():LiveData<UserInfo>

}