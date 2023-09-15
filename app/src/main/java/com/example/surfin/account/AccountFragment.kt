package com.example.surfin.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.surfin.R
import com.example.surfin.home.HomeViewModel

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)

        viewModel.getCwaTide()
        return inflater.inflate(R.layout.fragment_account, container, false)
    }



}