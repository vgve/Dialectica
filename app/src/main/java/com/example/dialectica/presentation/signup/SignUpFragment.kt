package com.example.dialectica.presentation.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dialectica.databinding.FragmentSignupBinding
import com.example.dialectica.presentation.MyApplication
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.afterTextChanged
import com.example.dialectica.utils.viewModelFactory
import kotlinx.coroutines.launch

class SignUpFragment: Fragment() {
    private lateinit var _binding: FragmentSignupBinding

    private val viewModel: SignUpViewModel by viewModels(
        factoryProducer = {
            viewModelFactory {
                SignUpViewModel(
                    MyApplication.appModule.sharedPrefsRepository
                )
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeUiState()
    }

    private fun observeUiState() {
        Log.d(this.TAG, "observeUiState")
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    Log.d(this.TAG, "$state")
                    with(_binding) {

                        etName.afterTextChanged {
                            viewModel.setName(it.trim())
                        }

                        btnSave.apply {
                            isEnabled = !state.username.isNullOrEmpty()
                            setOnClickListener {
                                viewModel.signUp()
                            }
                        }
                    }
                }
            }
        }
    }
}
