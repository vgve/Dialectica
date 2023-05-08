package com.example.dialectica.ui.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.utils.REPOSITORY
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()

    fun onDeleteQuestion(question: DialectQuestion, onSuccess: () -> Unit) {
        Log.d(TAG, "OnDeleteQuestion")
        viewModelScope.launch (Dispatchers.Main) {
            REPOSITORY.delete(question)
            getFavQuestions()
            onSuccess()
        }
    }

    fun getFavQuestions() {
        Log.d(TAG, "getFavQuestions")
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    questions = REPOSITORY.getFavQuestions()
                )
            }
        }
    }
}

data class FavouriteUiState(
    val questions: List<DialectQuestion> = emptyList()
)
