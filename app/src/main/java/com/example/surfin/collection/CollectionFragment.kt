package com.example.surfin.collection

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surfin.MainViewModel
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentCollectionBinding
import com.example.surfin.factory.CollectionFactory

class CollectionFragment : Fragment() {

    private lateinit var viewModel: CollectionViewModel
    private lateinit var binding: FragmentCollectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(
            this,
            CollectionFactory(repository)
        ).get(CollectionViewModel::class.java)
        binding = FragmentCollectionBinding.inflate(inflater)

        val mainViewModel: MainViewModel by activityViewModels()

        val adapter = CollectionAdapter(CollectionAdapter.OnClickListener {
            findNavController().navigate(
                CollectionFragmentDirections.actionNavigateToDetailFragment(
                    it
                )
            )
            mainViewModel.selectedSpotDetail = it
        })
        binding.collectionRecyclerView.adapter = adapter

        viewModel.spotCollection.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.i("collection overview", "$it")
        }

        viewModel.spotCollection.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.collectionHint.visibility = View.VISIBLE
            } else {
                binding.collectionHint.visibility = View.GONE
            }
        }

        return binding.root
    }

}