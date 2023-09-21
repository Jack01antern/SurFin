package com.example.surfin.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.surfin.R
import com.example.surfin.databinding.DialogDetailBinding
import com.example.surfin.factory.DetailFactory
import com.example.surfin.weather.WeatherFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DetailDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: DialogDetailBinding
    private val args by navArgs<DetailDialogArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogDetailBinding.inflate(inflater)
        viewModel = ViewModelProvider(this, DetailFactory(args)).get(DetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.locationTitle.text = args.tempId.title
        Log.i("explore detail","$args")



        return binding.root
    }
}