package com.example.dialectica.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dialectica.themes.DialectTheme
import com.example.dialectica.themes.Themes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                themeList = Themes().themeList
            )
        }
    }

    fun onClickTheme(theme: DialectTheme) {

    }
}

data class HomeUiState(
    val themeList: List<DialectTheme> = emptyList()
)
