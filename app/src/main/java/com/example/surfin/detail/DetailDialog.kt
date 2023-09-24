package com.example.surfin.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.surfin.SurfinApplication
import com.example.surfin.data.SurfinRepository
import com.example.surfin.databinding.DialogDetailBinding
import com.example.surfin.factory.DetailFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DialogDetailBinding
    private val args by navArgs<DetailDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDetailBinding.inflate(inflater)
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(this, DetailFactory(repository,args)).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        //star function
        val starredBtn = binding.btnStarred
        val unStarredBtn = binding.btnUnstarred
//
//        if (viewModel.dataStore.data == viewModel.dataStore.data) {
//            unStarredBtn.visibility = View.GONE
//            starredBtn.visibility = View.VISIBLE
//        } else {
//            unStarredBtn.visibility = View.VISIBLE
//            starredBtn.visibility = View.GONE
//        }
//
//
//        unStarredBtn.setOnClickListener {
//            unStarredBtn.visibility = View.GONE
//            starredBtn.visibility = View.VISIBLE
//            lifecycleScope.launch {
////                viewModel.addToCollection("lat",args.spotInfo.lat)
//                Log.i("STARRED3", viewModel.dataStore.data.toString())
//            }
//        }
//
//        starredBtn.setOnClickListener {
//            starredBtn.visibility = View.GONE
//            unStarredBtn.visibility = View.VISIBLE
//            lifecycleScope.launch {
////                viewModel.readCollection("lat")
//                Log.i("STARRED2", viewModel.dataStore.data.toString())
//            }
//        }


        return binding.root
    }


}