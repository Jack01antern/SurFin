package com.example.surfin.language

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.surfin.R

class LanguageDialog : AppCompatDialogFragment() {

    private lateinit var viewModel: LanguageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LanguageViewModel::class.java)

        return inflater.inflate(R.layout.dialog_language, container, false)
    }

}