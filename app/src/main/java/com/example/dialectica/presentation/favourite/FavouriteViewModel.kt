package com.example.dialectica.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository
import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.utils.REPOSITORY
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val isInit = sharedPrefsRepository.getInitUser()
        _uiState.update { it.copy(isInit = isInit) }
    }

    fun onDeleteQuestion(question: DialectQuestion, onSuccess: () -> Unit) {
        Log.d(TAG, "OnDeleteQuestion")
        viewModelScope.launch (Dispatchers.Main) {
            REPOSITORY.deleteFavourite(question)
            getFavQuestions()
            onSuccess()
        }
    }

    fun getFavQuestions() {
        Log.d(TAG, "getFavQuestions")
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    questions = REPOSITORY.getFavouriteList()
                )
            }
        }
    }
}

data class FavouriteUiState(
    val isInit: Boolean = false,
    val questions: List<DialectQuestion> = emptyList()
)
