package com.example.surfin.emergency

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.R

class EmergencyFragment : Fragment() {

    private lateinit var viewModel: EmergencyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EmergencyViewModel::class.java)

        return inflater.inflate(R.layout.fragment_emergency, container, false)
    }
}