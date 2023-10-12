package com.example.surfin.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(repository: SurfinRepository, args: Spots) : ViewModel() {

    var selectedDetail = MutableLiveData<Spots>()
//    var selectedDetail LiveData<Spots>
//        get() = _selectedDetail


//    private var _detailPhoto = MutableLiveData<MutableList<String>>()
//    val detailPhoto:LiveData<MutableList<String>>
//        get() = _detailPhoto
//    private val _spotCollection: LiveData<List<Spots>> = repository.getAllCollection()
//
//    val spotCollection: LiveData<List<Spots>>
//        get() = _spotCollection


    private var _isStarred = MutableLiveData<Boolean>()
    val isStarred: LiveData<Boolean>
        get() = _isStarred


    init {
        selectedDetail.value =  args
        Log.i("selectedDetail", "$args")
        checkIfStarred(repository,args)
    }


    //set up star function
    private fun checkIfStarred(repository: SurfinRepository, args: Spots) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val list = repository.getAllCollection()
                    _isStarred.value = list.value?.any { it.lat == args.lat && it.longitude == args.longitude } == true
                } catch (e: Exception) {
                    Log.i("detail db", "failed: ${e.message}")
                }
            }
        }
    }

    fun addToCollection(repository: SurfinRepository, spots: Spots) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.addToCollection(spots)
                    _isStarred.value = true
                }
            } catch (e: Exception) {
                Log.i("collection", "failed: ${e.message}")
            }
        }

    }

    fun removeFromCollection(repository: SurfinRepository, spots: Spots) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    repository.removeCollection(spots.lat, spots.longitude)
                    _isStarred.value = false
                }
            } catch (e: Exception) {
                Log.i("collection", "failed: ${e.message}")
            }
        }
    }
}
