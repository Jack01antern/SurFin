package com.example.surfin.data.localsource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.surfin.data.Spots
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.data.UserInfo

@Dao
interface SurfinDatabaseDao {
    @Insert
    fun insertHistory(userActivityHistory: UserActivityHistory)

    @Update
    fun updateHistory(userActivityHistory: UserActivityHistory)

    @Query("DELETE from user_history WHERE activityId = :activityId ")
    fun removeFromHistory(activityId: Long)

    @Query("DELETE FROM user_history")
    fun clearHistory()

    @Query("SELECT * FROM user_history ORDER BY date ASC")
    fun getAllHistory(): LiveData<List<UserActivityHistory>>


    @Insert
    fun addToCollection(spots: Spots)


    @Query("SELECT * FROM user_collection ORDER BY id ASC")
    fun getAllCollectionLiveData(): LiveData<List<Spots>>

    @Query("SELECT * FROM user_collection ORDER BY id ASC")
    fun getAllCollection(): List<Spots>


    @Query("DELETE from user_collection WHERE lat = :lat AND longitude = :longitude")
    fun removeCollection(lat: Double, longitude: Double)

    @Upsert
    fun updateUserInfo(userInfo: UserInfo)


    @Query("SELECT * FROM user_info WHERE id = :id")
    fun getUserInfo(id:Int): LiveData<UserInfo?>

}