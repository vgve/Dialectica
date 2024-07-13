package com.vicgcode.dialectica.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.domain.usecases.GetAuthorizeUseCase
import com.vicgcode.dialectica.presentation.extensions.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository
): ViewModel() {

    private val getAuthorizeUseCase = GetAuthorizeUseCase(sharedPrefsRepository)

    private val _uiAction: Channel<MainAction> = Channel()
    val uiAction = _uiAction.receiveAsFlow()

    init {
        val isAuthorized = getAuthorizeUseCase.invoke()
        Log.e(TAG, "isAuthorized $isAuthorized")
        viewModelScope.launch(Dispatchers.Main) {
            if (isAuthorized) {
                _uiAction.send(MainAction.OpenHomeScreen)
            } else {
                _uiAction.send(MainAction.OpenAuthScreen)
            }
        }
    }

}

sealed class MainAction {
    object OpenAuthScreen : MainAction()
    object OpenHomeScreen : MainAction()
}
