package com.vicgcode.dialectica.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.domain.usecases.GetAuthorizeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthorizeUseCase: GetAuthorizeUseCase
): ViewModel() {

    private val _state = MutableStateFlow<SplashState>(SplashState.Loading)
    // val state: StateFlow<SplashState> = _state
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val isAuthorized = getAuthorizeUseCase.invoke()
                if (isAuthorized) {
                    _state.value = SplashState.Home
                } else {
                    _state.value = SplashState.Login
                }
            } catch (exception: Exception) {
                _state.value = SplashState.Error(message = exception.message)
            }
        }
    }

}

sealed class SplashState {
    data object Loading : SplashState()
    data object Login : SplashState()
    data object Home : SplashState()
    data class Error(val message: String?) : SplashState()
}
