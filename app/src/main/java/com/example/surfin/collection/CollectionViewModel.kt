package com.example.surfin.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository

class CollectionViewModel(repository: SurfinRepository) : ViewModel() {

    val spotCollection: LiveData<List<Spots>> = repository.getAllCollection()

}