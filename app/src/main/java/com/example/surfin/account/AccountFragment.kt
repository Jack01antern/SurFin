package com.example.surfin.account

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.surfin.MainActivity
import com.example.surfin.R
import com.example.surfin.databinding.FragmentAccountBinding
import com.example.surfin.home.HomeViewModel
import java.util.Locale

class AccountFragment : Fragment() {


    private lateinit var viewModel: AccountViewModel
    private lateinit var locale: Locale
    private lateinit var binding: FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater)

        var languageList = ArrayList<String>()
        languageList.add("")
        languageList.add("English")
        languageList.add("Chinese")


        var adapter = ArrayAdapter(
            requireContext(),
            com.bumptech.glide.R.layout.support_simple_spinner_dropdown_item,
            languageList
        )

        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when (p2) {
                    0 -> {


                    }

                    1 -> setLocale("en")
                    2 -> setLocale("cn")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        binding.btnActivityHistory.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_history_fragment)
        }
        binding.btnCollection.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_collection_fragment)
        }
        binding.btnLanguage.setOnClickListener {
                binding.spinner.visibility = View.VISIBLE
        }

        binding.btnProvideSpots.setOnClickListener {
            findNavController().navigate(R.id.action_navigate_to_recommend_fragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
    }

    fun setLocale(language: String) {
        locale = Locale(language)
        var res = resources
        var dm = res.displayMetrics
        var conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)

        var refresh = Intent(requireContext(), MainActivity::class.java)
        startActivity(refresh)
        binding.spinner.visibility = View.GONE

    }


}