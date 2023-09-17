package com.example.surfin.data.localsource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surfin.data.UserActivityHistory

@Dao
interface SurfinDatabaseDao {
    @Insert
    fun insert(userActivityHistory: UserActivityHistory)

    @Update
    fun update(userActivityHistory: UserActivityHistory)

    @Query("DELETE from user_history WHERE location_title = :id AND date = :date")
    fun delete(id:String, date:Long)

    @Query("DELETE FROM user_history")
    fun clear()

    @Query("SELECT * FROM user_history ORDER BY date ASC")
    fun getAllInfo():
            LiveData<List<UserActivityHistory>>



}