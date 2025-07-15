package com.vicgcode.dialectica.presentation.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vicgcode.dialectica.R
import com.vicgcode.dialectica.databinding.FragmentSignupBinding
import com.vicgcode.dialectica.presentation.MyApplication
import com.vicgcode.dialectica.presentation.extensions.TAG
import com.vicgcode.dialectica.presentation.extensions.activityNavController
import com.vicgcode.dialectica.presentation.extensions.afterTextChanged
import com.vicgcode.dialectica.presentation.extensions.navigateSafely
import com.vicgcode.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class SignUpFragment: Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding

    private val viewModel: SignUpViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                SignUpViewModel(
                    MyApplication.appModule.sharedPrefsRepository,
                    MyApplication.appModule.appRoomRepository
                )
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
        observeUiAction()
    }

    private fun observeUiState() {
        Log.d(TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(TAG, "$state")
                    with(binding) {

                        etName.afterTextChanged {
                            viewModel.setName(it.trim())
                        }

                        btnSave.apply {
                            isEnabled = !state.username.isNullOrEmpty()
                            setOnClickListener {
                                Log.e(TAG, "onClickListener btnSave")
                                viewModel.signUp()
                            }
                            setBackgroundColor(
                                if (isEnabled) ContextCompat.getColor(
                                    context,
                                    R.color.system_secondary
                                ) else ContextCompat.getColor(context, R.color.light_grey)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun observeUiAction() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiAction.collect { uiAction ->
                    when (uiAction) {
                        is SignUpAction.OnAuthSuccess -> {
                            Log.d(TAG, "OnAuthSuccess")
                            activityNavController().navigateSafely(R.id.action_global_baseFlowFragment)
                        }
                    }
                }
            }
        }
    }
}
