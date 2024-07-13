package com.vicgcode.dialectica.presentation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.FlowFragmentBaseBinding
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.utils.viewModelFactory

class BaseFlowFragment: Fragment(R.layout.flow_fragment_base) {

    private lateinit var binding: FlowFragmentBaseBinding
    private lateinit var navController: NavController

    private val viewModel: BaseFlowViewModel by viewModels(
        factoryProducer = { viewModelFactory { BaseFlowViewModel() } }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FlowFragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_base) as NavHostFragment
        navController = navHostFragment.navController

        setupNavigation()
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }
}
