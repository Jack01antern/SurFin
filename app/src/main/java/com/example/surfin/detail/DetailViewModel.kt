package com.example.surfin.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import kotlinx.coroutines.launch

class DetailViewModel(repository: SurfinRepository, args: DetailDialogArgs) : ViewModel() {

    private val _selectedDetail = MutableLiveData<DetailDialogArgs>()
    val selectedDetail: LiveData<DetailDialogArgs>
        get() = _selectedDetail

//    private val spotCollection = repository.getAllCollection()

//    val spotCollection:LiveData<List<Spots>>
//        get() = _spotCollection

    init {
        _selectedDetail.value = args
        Log.i("selectedProduct", "$args")
    }


    //set up star function
//    private fun isStarred(spots: Spots): Boolean {
//        return spotCollection.value!!.any { it.lat == spots.lat && it.long == spots.long }
//    }
//
//
//    fun addToCollection(repository: SurfinRepository, spots: Spots) {
//        var collection = mutableListOf<Spots>()
//        if (!isStarred(spots)) {
//            viewModelScope.launch {
//                repository.addToCollection(spots)
//            }
//        }
//    }
//
//
//    fun removeFromCollection(repository: SurfinRepository, spots: Spots) {
//        if (isStarred(spots)) {
//            viewModelScope.launch {
//                repository.removeCollection(spots.lat, spots.long)
//            }
//        }
//    }

}