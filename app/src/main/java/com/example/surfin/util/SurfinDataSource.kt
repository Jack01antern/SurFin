package com.example.surfin.util

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.surfin.data.CwaEarthquakeResult
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.localsource.SurfinDatabaseDao
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class SurfinDataSource(private val dao: SurfinDatabaseDao, private val db: FirebaseFirestore) :
    SurfinRepository {

    //remote
    override suspend fun getCwaTide(): CwaTideResult {
        return super.getCwaTide()
    }

    override suspend fun getCwaTemp(): CwaTempResult {
        return super.getCwaTemp()
    }

    override suspend fun getCwaWdsd(locationName: String): CwaTempResult {
        return super.getCwaWdsd(locationName = locationName)
    }

    override suspend fun getCwaUvi(): CwaUviResult {
        return super.getCwaUvi()
    }

    override suspend fun getCwaEarthquake(): CwaEarthquakeResult {
        return super.getCwaEarthquake()
    }

    //firebase
    override fun getFirebaseSpotData() {
        TODO("Not yet implemented")
    }

    override fun observeFirebaseSpotData() {
        db.collection("spots").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("retrieve??", e)
                return@addSnapshotListener
            }
        }
    }

    override fun setFirebaseSpotInfo(spots: Spots) {
        TODO("Not yet implemented")
    }


    //local
    override suspend fun insert(user: UserActivityHistory) {
        return dao.insert(user)
    }

    override suspend fun clear() {
        return dao.clear()
    }

    override fun getAllHistory(): LiveData<List<UserActivityHistory>> {
        return dao.getAllHistory()
    }
}