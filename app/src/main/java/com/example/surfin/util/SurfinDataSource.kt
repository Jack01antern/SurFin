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
import com.google.android.gms.common.api.internal.ApiKey
import com.google.firebase.firestore.FirebaseFirestore

class SurfinDataSource(private val dao: SurfinDatabaseDao, private val db: FirebaseFirestore) :
    SurfinRepository {

    //remote
    override suspend fun getCwaTide(apiKey: String,locationName: String): CwaTideResult {
        return super.getCwaTide(apiKey,locationName)
    }

    override suspend fun getCwaTemp(apiKey: String,locationName: String): CwaTempResult {
        return super.getCwaTemp(apiKey,locationName)
    }

    override suspend fun getCwaWdsd(apiKey: String, locationName: String): CwaTempResult {
        return super.getCwaWdsd(apiKey, locationName)
    }

    override suspend fun getCwaWeather(apiKey: String, locationName: String): CwaTempResult {
        return super.getCwaWeather(apiKey, locationName)
    }

    override suspend fun getCwaUvi(apiKey: String, locationName: String): CwaUviResult {
        return super.getCwaUvi(apiKey,locationName)
    }

//    override suspend fun getCwaEarthquake(locationName: String): CwaEarthquakeResult {
//        return super.getCwaEarthquake(locationName)
//    }

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
    override suspend fun insertHistory(user: UserActivityHistory) {
        return dao.insertHistory(user)
    }

    override suspend fun clearHistory() {
        return dao.clearHistory()
    }

    override fun getAllHistory(): LiveData<List<UserActivityHistory>> {
        return dao.getAllHistory()
    }

    override fun addToCollection(spots: Spots) {
        return dao.addToCollection(spots)
    }

    override fun getAllCollection(): LiveData<List<Spots>> {
        return dao.getAllCollection()
    }

    override fun removeCollection(lat: Double, longitude: Double) {
        return dao.removeCollection(lat, longitude)
    }
}