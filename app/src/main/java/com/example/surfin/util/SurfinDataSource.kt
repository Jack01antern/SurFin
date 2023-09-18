package com.example.surfin.util

import android.util.Log
import androidx.lifecycle.LiveData
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

    override suspend fun getCwaWdsd(locationName:String): CwaTempResult {
        return super.getCwaWdsd(locationName = locationName)
    }

    override suspend fun getCwaUvi(): Flow<CwaUviResult> {
        return super.getCwaUvi()
    }


    //firebase
    override fun getFirebaseSpotInfo() {
        TODO("Not yet implemented")
    }

    override fun setFirebaseSpotInfo(spots: Spots) {
        db.collection("spots").document("LA")
            .set(spots)
            .addOnSuccessListener { Log.d("firebase", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("firebase ", "Error writing document", e) }
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