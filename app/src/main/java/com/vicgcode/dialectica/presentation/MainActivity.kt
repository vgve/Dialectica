package com.vicgcode.dialectica.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.ActivityMainBinding
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                MainViewModel(
                    MyApplication.appModule.sharedPrefsRepository
                )
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        observeUiAction()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }


    private fun observeUiAction() {
        lifecycleScope.launch {
            this@MainActivity.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { uiAction ->
                    when (uiAction) {
                        is MainAction.OpenAuthScreen -> {
                            Log.e(TAG, "OpenAuthScreen")
                            val navGraph = navController.navInflater.inflate(R.navigation.start_graph)
                            navGraph.setStartDestination(R.id.signUpFragment)
                            navController.graph = navGraph
                        }
                        is MainAction.OpenHomeScreen -> {
                            val navGraph = navController.navInflater.inflate(R.navigation.start_graph)
                            navGraph.setStartDestination(R.id.baseFlowFragment)
                            navController.graph = navGraph
                        }
                    }
                }
            }
        }
    }
}
