package com.example.surfin.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentHistoryBinding
import com.example.surfin.factory.HistoryFactory

class HistoryFragment : Fragment() {

    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHistoryBinding.inflate(inflater)
        val repository = (requireContext().applicationContext as SurfinApplication).surfinRepository
        viewModel =
            ViewModelProvider(this, HistoryFactory(repository)).get(HistoryViewModel::class.java)

        val adapter = HistoryAdapter()
        binding.historyRecyclerView.adapter = adapter
        viewModel.activityHistory.observe(viewLifecycleOwner, Observer {
            adapter.submitList(viewModel.activityHistory.value)
        })

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_add_history_fragment)
        }


        return binding.root
    }
}