package com.example.surfin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.surfin.databinding.ActivityMainBinding
import com.example.surfin.util.CurrentFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //button navigation
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = binding.bottomNav
        bottomNav.setupWithNavController(navController)

        //toolbar
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        setupBottomNav()
        setupNavController()
        setupToolbar()
    }

    private fun setupBottomNav() {
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigate_to_home_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.explore -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigate_to_explore_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.emergency -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigate_to_emergency_fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.account -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.action_navigate_to_account_fragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }


    private fun setupNavController() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.homeFragment -> binding.toolbarTitle.text = CurrentFragment.HOME.value
                R.id.weatherFragment -> binding.toolbarTitle.text =
                    CurrentFragment.WEATHER.value

                R.id.exploreFragment -> binding.toolbarTitle.text =
                    CurrentFragment.EXPLORE.value

                R.id.emergencyFragment -> binding.toolbarTitle.text =
                    CurrentFragment.EMERGENCY.value

                R.id.accountFragment -> binding.toolbarTitle.text =
                    CurrentFragment.ACCOUNT.value

                R.id.historyFragment -> binding.toolbarTitle.text =
                    CurrentFragment.HISTORY.value

                R.id.collectionFragment -> binding.toolbarTitle.text =
                    CurrentFragment.COLLECTION.value

                R.id.languageFragment -> binding.toolbarTitle.text =
                    CurrentFragment.LANGUAGE.value
            }
            Log.i("toolbar", "toolbar value: ${viewModel.currentFragment.value}")
        }
    }

    private fun setupToolbar() {
        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            when (navController.currentDestination?.id) {
                R.id.homeFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.weatherFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.exploreFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.emergencyFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.accountFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.historyFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.collectionFragment -> binding.toolbar.visibility = View.VISIBLE
                R.id.languageFragment -> binding.toolbar.visibility = View.VISIBLE
            }
            Log.i("toolbar", "toolbar value: ${viewModel.currentFragment.value}")
        }
    }
}