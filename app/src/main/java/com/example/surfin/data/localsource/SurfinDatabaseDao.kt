package com.example.surfin.data.localsource

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.surfin.data.User

@Dao
interface SurfinDatabaseDao {
    @Insert
    fun insert(user: User)

    @Update
    fun update(user:User)

    @Query("DELETE from user_info WHERE user_id = :id")
    fun delete(id:String)

    @Query("DELETE FROM user_info")
    fun clear()

    @Query("SELECT * FROM user_info ORDER BY user_id ASC")
    fun getAllInfo():
            LiveData<List<User>>

    @Query("SELECT * from user_info WHERE user_id = :id")
    fun get(id: String): User?

}