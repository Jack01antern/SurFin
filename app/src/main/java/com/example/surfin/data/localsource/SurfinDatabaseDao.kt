package com.example.surfin.data.localsource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surfin.data.Spots
import com.example.surfin.data.UserActivityHistory

@Dao
interface SurfinDatabaseDao {
    @Insert
    fun insertHistory(userActivityHistory: UserActivityHistory)

    @Update
    fun updateHistory(userActivityHistory: UserActivityHistory)

    @Query("DELETE from user_history WHERE location_title = :id AND date = :date")
    fun deleteHistory(id: String, date: Long)

    @Query("DELETE FROM user_history")
    fun clearHistory()

    @Query("SELECT * FROM user_history ORDER BY date ASC")
    fun getAllHistory():
            LiveData<List<UserActivityHistory>>


    @Query("SELECT * FROM user_history WHERE activityId = :id ORDER BY date ASC")
    fun getCertainHistory(id:Long):
            LiveData<UserActivityHistory>

    @Insert
    fun addToCollection(spots: Spots)


    @Query("SELECT * FROM user_collection ORDER BY id ASC")
    fun getAllCollection():
            LiveData<List<Spots>>

    @Query("DELETE from user_collection WHERE lat = :lat AND longitude = :longitude")
    fun removeCollection(lat: Double, longitude: Double)

}