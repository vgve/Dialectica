package com.example.dialectica.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.core.domain.SharedPrefsKeys
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction: Channel<SignUpAction> = Channel()
    val uiAction = _uiAction.receiveAsFlow()

    fun setName(name: String) {
        _uiState.update {
            it.copy(
                username = name
            )
        }
    }

    fun signUp() {
        sharedPrefsRepository.setUserName(uiState.value.username)
        sharedPrefsRepository.setAuthorize(true)

        viewModelScope.launch(Dispatchers.Main) {
            _uiAction.send(SignUpAction.OnAuthSuccess)
        }
    }

}

data class SignUpUiState(
    val username: String? = null,
)

sealed class SignUpAction {
    object OnAuthSuccess : SignUpAction()
}
