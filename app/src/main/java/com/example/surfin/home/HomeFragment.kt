package com.example.surfin.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputFilter
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.callApi.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_weather_fragment)
        }

        return binding.root
    }

}