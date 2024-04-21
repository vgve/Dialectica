package com.example.dialectica.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository
import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.database.room.AppRoomRepository
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouriteViewModel(
    sharedPrefsRepository: SharedPrefsRepository,
    private val appRoomRepository: AppRoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val isInit = sharedPrefsRepository.isAuthorize()
        _uiState.update {
            it.copy(
                isInit = isInit
            )
        }
    }

    fun onDeleteQuestion(question: DialectQuestion, onSuccess: () -> Unit) {
        Log.d(TAG, "OnDeleteQuestion")
        viewModelScope.launch (Dispatchers.Main) {
            appRoomRepository.deleteFavourite(question)
            updateFavourites()
            onSuccess()
        }
    }

    fun updateFavourites() {
        Log.d(TAG, "getFavQuestions")
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    questions = appRoomRepository.getFavouriteList()
                )
            }
        }
    }
}

data class FavouriteUiState(
    val isInit: Boolean = false,
    val questions: List<DialectQuestion> = emptyList()
)
