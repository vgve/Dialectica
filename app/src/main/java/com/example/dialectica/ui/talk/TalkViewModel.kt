package com.example.dialectica.ui.talk

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TalkViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TalkUiState())
    val uiState = _uiState.asStateFlow()
}

data class TalkUiState(
    val username: String? = null
)


