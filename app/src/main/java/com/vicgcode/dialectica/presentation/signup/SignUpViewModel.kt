package com.vicgcode.dialectica.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.database.room.AppRoomRepository
import com.vicgcode.dialectica.domain.usecases.SetAuthorizeUseCase
import com.vicgcode.dialectica.domain.usecases.SetUsernameUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val appRoomRepository: AppRoomRepository
): ViewModel() {

    private val setAuthorizeUseCase = SetAuthorizeUseCase(sharedPrefsRepository)
    private val setUsernameUseCase = SetUsernameUseCase(sharedPrefsRepository)

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
        val user = DialectPerson(
            name = uiState.value.username.orEmpty(),
            interests = emptyList(),
            isOwner = true,
            questions = emptyList()
        )
        uiState.value.username?.let { setUsernameUseCase.invoke(it) }
        setAuthorizeUseCase.invoke(true)

        viewModelScope.launch(Dispatchers.Main) {
            appRoomRepository.insertPerson(user)
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
