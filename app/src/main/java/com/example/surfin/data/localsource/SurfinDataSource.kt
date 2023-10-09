package com.example.surfin.data.localsource

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.surfin.data.UserActivityHistory
import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.CwaWaveResult
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SurfinDataSource(private val dao: SurfinDatabaseDao, private val db: FirebaseFirestore) :
    SurfinRepository {

    //remote
    override suspend fun getCwaTide(apiKey: String, locationName: String): CwaTideResult {
        return super.getCwaTide(apiKey, locationName)
    }

    override suspend fun getCwaTemp(apiKey: String, locationName: String): CwaTempResult {
        return super.getCwaTemp(apiKey, locationName)
    }

    override suspend fun getCwaWdsd(apiKey: String, locationName: String): CwaTempResult {
        return super.getCwaWdsd(apiKey, locationName)
    }

    override suspend fun getCwaWeather(apiKey: String, locationName: String): CwaTempResult {
        return super.getCwaWeather(apiKey, locationName)
    }

    override suspend fun getCwaUvi(apiKey: String, locationName: String): CwaUviResult {
        return super.getCwaUvi(apiKey, locationName)
    }

    override suspend fun getCwaWave(apiKey: String, locationName: String): CwaWaveResult {
        return super.getCwaWave(apiKey, locationName)
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

    override suspend fun updateHistory(user: UserActivityHistory) {
        withContext(Dispatchers.IO) {
            dao.updateHistory(user)
        }
    }

    override suspend fun clearHistory() {
        withContext(Dispatchers.IO) {
            dao.clearHistory()
        }
    }

    override suspend fun removeFromHistory(activityId: Long) {
        withContext(Dispatchers.IO) {
            dao.removeFromHistory(activityId)
        }
    }

    override fun getAllHistory(): LiveData<List<UserActivityHistory>> {
        return dao.getAllHistory()
    }

    override suspend fun addToCollection(spots: Spots) {
        withContext(Dispatchers.IO) {
            dao.addToCollection(spots)
        }
    }

    override fun getAllCollectionLiveData(): LiveData<List<Spots>> {
        return dao.getAllCollectionLiveData()
    }

    override suspend fun getAllCollection(): List<Spots> {
        var list = listOf<Spots>()
        withContext(Dispatchers.IO) {
            list = dao.getAllCollection()
        }
        return list
    }

    override suspend fun removeCollection(lat: Double, longitude: Double) {
        withContext(Dispatchers.IO) {
            dao.removeCollection(lat, longitude)
        }
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        withContext(Dispatchers.IO) {
            dao.updateUserInfo(userInfo)
        }
    }

    override fun getUserInfo(): LiveData<UserInfo?> {
        return dao.getUserInfo()
    }
}