package com.example.surfin.language

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.R

class LanguageFragment : Fragment() {

    companion object {
        fun newInstance() = LanguageFragment()
    }

    private lateinit var viewModel: LanguageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_language, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LanguageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}