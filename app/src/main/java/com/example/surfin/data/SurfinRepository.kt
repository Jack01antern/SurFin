package com.example.surfin.data

import com.example.surfin.network.SurfinApi

interface SurfinRepository {

    //remote
    suspend fun getCwaTide(): CwaTideResult {
        return SurfinApi.retrofitService.getCwaTide()
    }

    suspend fun getCwaTemp(): CwaTempResult {
        return SurfinApi.retrofitService.getCwaTemp()
    }


    suspend fun getCwaWdsd(): CwaTempResult {
        return SurfinApi.retrofitService.getCwaWdsd()
    }


    suspend fun getCwaUvi(): CwaUviResult {
        return SurfinApi.retrofitService.getCwaUvi()
    }


    //local db
    suspend fun insert(user: UserActivityHistory)
}