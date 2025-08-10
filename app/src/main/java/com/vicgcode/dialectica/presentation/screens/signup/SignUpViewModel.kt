package com.vicgcode.dialectica.presentation.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.domain.usecases.AddPersonUseCase
import com.vicgcode.dialectica.domain.usecases.SetAuthorizeUseCase
import com.vicgcode.dialectica.domain.usecases.SetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val addPersonUseCase: AddPersonUseCase,
    private val setAuthorizeUseCase: SetAuthorizeUseCase,
    private val setUsernameUseCase: SetUsernameUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
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

        _uiState.update {
            it.copy(isSignUpSuccessful = true)
        }

        viewModelScope.launch(Dispatchers.Main) {
            addPersonUseCase.invoke(user)
            _uiAction.send(SignUpAction.OnAuthSuccess)
        }
    }

    fun handleEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.NameChanged -> setName(event.name)
            is SignUpEvent.OnSignUpClick -> signUp()
        }
    }

}

data class SignUpState(
    val username: String? = null,
    val isSignUpSuccessful: Boolean? = null
)

sealed class SignUpAction {
    data object OnAuthSuccess : SignUpAction()
}

sealed class SignUpEvent {
    data class NameChanged(val name: String) : SignUpEvent()
    data object OnSignUpClick : SignUpEvent()
}
