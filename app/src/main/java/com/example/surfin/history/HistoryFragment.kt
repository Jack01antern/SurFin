package com.example.surfin.history

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.telephony.CarrierConfigManager.Bsf
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.SurfinApplication
import com.example.surfin.databinding.FragmentHistoryBinding
import com.example.surfin.factory.HistoryFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

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

        val adapter = HistoryAdapter(HistoryAdapter.OnClickListener {
            findNavController().navigate(EditFragmentDirections.actionNavigateToEditFragment(it))
        Log.i("history edit btn","clicked: $it")})
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

