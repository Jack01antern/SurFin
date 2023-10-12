package com.example.surfin.data.localsource
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.surfin.data.Spots
//
//@Database(entities = [Spots::class], version = 1, exportSchema = false)
//abstract class SurfinCollectionDatabase : RoomDatabase() {
//
//    abstract val surfinDatabaseDao: SurfinDatabaseDao
//    companion object {
//        @Volatile
//        private var INSTANCE: SurfinCollectionDatabase? = null
//
//        fun getInstance(context: Context): SurfinCollectionDatabase {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        SurfinCollectionDatabase::class.java,
//                        "surfin_database"
//                    )
//                        .fallbackToDestructiveMigration()
//                        .build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}
