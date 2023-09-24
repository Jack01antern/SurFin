package com.example.surfin.collection

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.R
import com.example.surfin.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {

    private lateinit var viewModel: CollectionViewModel
    private lateinit var binding: FragmentCollectionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CollectionViewModel::class.java)
        binding = FragmentCollectionBinding.inflate(inflater)

        val adapter = CollectionAdapter()
        binding.collectionRecyclerView.adapter = adapter

        //haven't submit list yet!!!!!!!


        return binding.root
    }

}