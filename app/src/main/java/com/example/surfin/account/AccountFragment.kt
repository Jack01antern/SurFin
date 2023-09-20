package com.example.surfin.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.surfin.R
import com.example.surfin.databinding.FragmentAccountBinding
import com.example.surfin.home.HomeViewModel

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAccountBinding.inflate(inflater)

        binding.btnActivityHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_history_fragment)
        }
        binding.btnCollection.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_collection_fragment)
        }
        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_language_fragment)
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }
}