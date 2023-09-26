package com.example.surfin.collection

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
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
        viewModel = ViewModelProvider(this,CollectionFactory(repository)).get(CollectionViewModel::class.java)
        binding = FragmentCollectionBinding.inflate(inflater)

        val adapter = CollectionAdapter()
        binding.collectionRecyclerView.adapter = adapter

        //haven't submitted list yet!!!!!!!
        viewModel.spotCollection.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return binding.root
    }

}