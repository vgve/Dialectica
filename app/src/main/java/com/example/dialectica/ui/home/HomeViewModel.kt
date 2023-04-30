package com.example.dialectica.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dialectica.data.DialectQuestion
import com.example.dialectica.data.DialectTheme
import com.example.dialectica.data.Themes
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                themeList = Themes().themeList,
                allQuestions = Themes().questionList
            )
        }
    }

    fun onClickTheme(theme: DialectTheme) {
        Log.d(this.TAG, "onClickTheme")
        val questions = mutableListOf<DialectQuestion>()
        Themes().questionList.forEach {
            if (it.idTheme == theme.id) questions.add(it)
        }
        _uiState.update {
            it.copy(
                currentQuestionList = questions
            )
        }
    }

    fun onClickQuestion(question: DialectQuestion) {
        Log.d(this.TAG, "onClickQuestion")
        val questions = _uiState.value.currentQuestionList.map {
            if (question.id == it.id) {
                it.isChosen = true
            }
            it
        }

        _uiState.update {
            it.copy(
                currentQuestionList = questions
            )
        }
    }
}

data class HomeUiState(
    val themeList: List<DialectTheme> = emptyList(),
    val allQuestions: List<DialectQuestion> = emptyList(),
    val currentQuestionList: List<DialectQuestion> = emptyList()
)
