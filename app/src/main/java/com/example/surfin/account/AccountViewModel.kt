package com.example.surfin.account

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AccountViewModel(repository: SurfinRepository) : ViewModel() {

    val userInfo = repository.getUserInfo()
    private val db = FirebaseFirestore.getInstance()

    fun updateUserInfo(userInfo: UserInfo, repository: SurfinRepository) {
        viewModelScope.launch {
            try {
                repository.updateUserInfo(userInfo)
            } catch (e: Exception) {
                Log.i(" account user info", "${e.message}")
            }
        }
    }


    fun provideSpots(text: String) {
        db.collection("New Spots").document()
            .set(hashMapOf("content" to text))
            .addOnSuccessListener {
                Log.d(
                    "provide spots",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("provide spots", "Error writing document", e) }
    }



    fun contactUs(text: String) {
        db.collection("contact us").document()
            .set(hashMapOf("content" to text))
            .addOnSuccessListener {
                Log.d(
                    "provide spots",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("provide spots", "Error writing document", e) }
    }

    fun reportProblem(text: String) {
        db.collection("report problem").document()
            .set(hashMapOf("content" to text))
            .addOnSuccessListener {
                Log.d(
                    "provide spots",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("provide spots", "Error writing document", e) }
    }
}