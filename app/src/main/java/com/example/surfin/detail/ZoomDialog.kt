package com.example.surfin.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.surfin.databinding.DialogZoomBinding
import com.example.surfin.factory.ZoomFactory

class ZoomDialog :Fragment() {

    private lateinit var binding: DialogZoomBinding
    private lateinit var viewModel: ZoomViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogZoomBinding.inflate(inflater)
        val args = ZoomDialogArgs.fromBundle(requireArguments()).photoInfo
        viewModel = ViewModelProvider(this, ZoomFactory(args)).get(ZoomViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.backKey.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }
}