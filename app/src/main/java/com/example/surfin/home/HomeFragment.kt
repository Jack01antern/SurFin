package com.example.surfin.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
            Log.i("cwa args", "${it}")
        })


        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> binding.loadingAnim.visibility = View.VISIBLE
                false -> binding.loadingAnim.visibility = View.GONE
            }
        })

        binding.homeRecyclerView.adapter = adapter
        viewModel.fireResult.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })



        binding.searchCardView.setOnClickListener {
            binding.searchView.isIconified = false
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.searchFirebase(query!!)
                    Log.i("fire store", "p0: $query")
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) {
                        viewModel.getFirebase()
                    } else {
                        val searchHandler = Handler(Looper.getMainLooper())
                        searchHandler.removeCallbacksAndMessages(null)
                        searchHandler.postDelayed({
                            if (newText != "") {
                                viewModel.searchFirebase(newText.toString())
                            }
                            Log.i("Firestore", "Debounced query: $newText")
                        }, 500)
                    }
                    return false
                }
            })
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getFirebase()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

}