package com.example.surfin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.surfin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //button navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = binding.bottomNav
        bottomNav.setupWithNavController(navController)

        //toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_navigate_to_home_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.weather -> {
                    findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_navigate_to_weather_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.map -> {
                    findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_navigate_to_map_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.account -> {
                    findNavController(R.id.nav_host_fragment_container).navigate(R.id.action_navigate_to_account_fragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}