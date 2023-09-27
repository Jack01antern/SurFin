package com.example.surfin.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentHomeBinding
import com.example.surfin.factory.HomeFactory

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel = ViewModelProvider(this, HomeFactory(repository)).get(HomeViewModel::class.java)


        val adapter = HomeAdapter(HomeAdapter.OnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionNavigateToWeatherFragment(it))
            Log.i("cwa args","${it}")
        })


        binding.homeRecyclerView.adapter = adapter
        viewModel.fireResult.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchFirebase(p0!!)
                Log.i("fire store","p0: $p0")
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })


        return binding.root
    }

}