package com.example.surfin.account

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class AccountViewModel(repository: SurfinRepository) : ViewModel() {

    val userInfo = repository.getUserInfo(1)
    private val db = FirebaseFirestore.getInstance()

    var isEditing = MutableLiveData(false)

    fun updateUserInfo(userInfo: UserInfo, repository: SurfinRepository) {
        viewModelScope.launch {
            try {
                repository.updateUserInfo(userInfo)
            } catch (e: Exception) {
                Log.i(" account user info", "${e.message}")
            }
        }
    }


    fun provideSpots(address:String,content: String) {
        db.collection("New Spots").document()
            .set(hashMapOf(
                "address" to address,
                "content" to content))
            .addOnSuccessListener {
                Log.d(
                    "provide spots",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("provide spots", "Error writing document", e) }
    }



    fun contactUs(category:String,userName:String,userEmail:String,content: String) {
        db.collection("contact us").document()
            .set(hashMapOf(
                "category" to category,
                "user_name" to userName,
                "user_email" to userEmail,
                "content" to content))
            .addOnSuccessListener {
                Log.d(
                    "provide spots",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w("provide spots", "Error writing document", e) }
    }

}