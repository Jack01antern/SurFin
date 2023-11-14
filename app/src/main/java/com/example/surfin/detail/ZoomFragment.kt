package com.example.surfin.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.surfin.MainViewModel
import com.example.surfin.R
import com.example.surfin.databinding.DialogZoomBinding
import com.example.surfin.factory.ZoomFactory

class ZoomFragment : Fragment() {

    private lateinit var binding: DialogZoomBinding
    private lateinit var viewModel: ZoomViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogZoomBinding.inflate(inflater)
        val args = ZoomFragmentArgs.fromBundle(requireArguments()).photoInfo
        viewModel = ViewModelProvider(this, ZoomFactory(args)).get(ZoomViewModel::class.java)

        val mainViewModel: MainViewModel by activityViewModels()


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.backKey.setOnClickListener {
            findNavController().navigateUp()
            val spot = mainViewModel.selectedSpotDetail
            Log.i("zoom", "spot:$spot")
            Log.i("zoom", "spot:${mainViewModel.selectedSpotDetail}")
            findNavController().navigate(
                DetailDialogDirections.actionNavigateToDetailFragment(spot)
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                    val spot = mainViewModel.selectedSpotDetail
                    Log.i("zoom", "spot:$spot")
                    findNavController().navigate(
                        DetailDialogDirections.actionNavigateToDetailFragment(spot)
                    )
                }
            })

        return binding.root
    }

}