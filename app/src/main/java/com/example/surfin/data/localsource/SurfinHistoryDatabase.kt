package com.example.surfin.data.localsource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.surfin.data.Spots
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.data.UserInfo

@Database(entities = [UserActivityHistory::class, Spots::class,UserInfo::class], version = 1, exportSchema = false)
abstract class SurfinHistoryDatabase : RoomDatabase() {

    abstract val surfinDatabaseDao: SurfinDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: SurfinHistoryDatabase? = null

        fun getInstance(context: Context): SurfinHistoryDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SurfinHistoryDatabase::class.java,
                        "surfin_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
