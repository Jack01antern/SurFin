package com.example.surfin.emergency

import android.content.Intent
import android.net.Uri
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmergencyBinding.inflate(inflater)

        binding.btnCall118.setOnClickListener {
            startActivity(viewModel.dial118())
        }

        binding.btnCall112.setOnClickListener {
            startActivity(viewModel.dial112())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EmergencyViewModel::class.java)
    }
}