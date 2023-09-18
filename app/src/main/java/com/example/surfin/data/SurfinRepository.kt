package com.example.surfin.data

import androidx.lifecycle.LiveData
import com.example.surfin.network.SurfinApi
import kotlinx.coroutines.flow.Flow

interface SurfinRepository {

    //remote api
    suspend fun getCwaTide(): CwaTideResult {
        return SurfinApi.retrofitService.getCwaTide()
    }

    suspend fun getCwaTemp(): CwaTempResult {
        return SurfinApi.retrofitService.getCwaTemp()
    }


    suspend fun getCwaWdsd(locationName:String): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWdsd(locationName = locationName)
    }


    suspend fun getCwaUvi(): Flow<CwaUviResult> {
        return SurfinApi.retrofitService.getCwaUvi()
    }

    //firebase
    fun getFirebaseSpotInfo()

    fun setFirebaseSpotInfo(spots: Spots)

    //local db
    suspend fun insert(user: UserActivityHistory)

    suspend fun clear()

    fun getAllHistory(): LiveData<List<UserActivityHistory>>
}