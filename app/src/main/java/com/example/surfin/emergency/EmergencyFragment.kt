package com.example.surfin.emergency

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.R
import com.example.surfin.data.localsource.SurfinDatabaseDao
import com.example.surfin.databinding.FragmentEmergencyBinding

class EmergencyFragment : Fragment() {

    private lateinit var viewModel: EmergencyViewModel
    private lateinit var binding:FragmentEmergencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmergencyBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(EmergencyViewModel::class.java)
//        binding.btnSubmit.setOnClickListener {
//            SurfinDatabaseDao
//        }

        return binding.root
    }
}