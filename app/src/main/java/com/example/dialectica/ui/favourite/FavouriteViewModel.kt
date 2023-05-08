package com.example.dialectica.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
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

    init {}

    fun onDeleteQuestion(question: DialectQuestion) {
        delete(question)
    }

    private fun delete(note: DialectQuestion) {
        Log.d(TAG, "OnDeleteQuestion")
        viewModelScope.launch (Dispatchers.IO) {
            REPOSITORY.delete(note) {
                viewModelScope.launch (Dispatchers.Main) {
                }
            }
        }
    }

    fun getFavQuestions() : LiveData<List<DialectQuestion>> {
        Log.d(TAG, "getFavQuestions: ${REPOSITORY.favQuestions}")
        // get LiveData from REPOSITORY
        return REPOSITORY.favQuestions
    }

    fun setFavQuestionList(questions: List<DialectQuestion>) {
        _uiState.update {
            it.copy(
                questions = questions
            )
        }
    }
}

data class FavouriteUiState(
    val questions: List<DialectQuestion> = emptyList()
)
