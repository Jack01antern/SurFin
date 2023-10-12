package com.example.surfin.util

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.localsource.SurfinHistoryDatabase
import com.google.firebase.firestore.FirebaseFirestore

object ServiceLocator {
    @Volatile
    var surfinRepository: SurfinRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): SurfinRepository {
        synchronized(this) {
            return surfinRepository
                ?: createSurfinRepository(context)
        }
    }

    private fun createSurfinRepository(context: Context): SurfinRepository {
        return SurfinDataSource(SurfinHistoryDatabase.getInstance(context).surfinDatabaseDao,
            FirebaseFirestore.getInstance())
    }

//    private fun createLocalDataSource(context: Context): SurfinDataSource {
//        return SurfinDataSource(SurfinDatabase.getInstance(context).surfinDatabaseDao,
//            FirebaseFirestore.getInstance())
//    }
}