package com.example.surfin.data

interface SurfinRepository {

    suspend fun getCwaTide()
    suspend fun insert(user: User)
}