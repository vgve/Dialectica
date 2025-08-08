package com.vicgcode.dialectica.presentation.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.domain.usecases.DeleteFavouriteUseCase
import com.vicgcode.dialectica.domain.usecases.GetFavouritesUseCase
import com.vicgcode.dialectica.presentation.extensions.TAG
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
class FavouriteViewModel @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction: Channel<FavouriteAction> = Channel()
    val uiAction = _uiAction.receiveAsFlow()

    init {
        val isInit = sharedPrefsRepository.isAuthorize()
        _uiState.update {
            it.copy(
                isInit = isInit
            )
        }
    }

    fun onSwipeToDeleteQuestion(question: DialectQuestion) {
        Log.d(TAG, "OnSwipeDeleteQuestion")
        viewModelScope.launch (Dispatchers.Main) {
            _uiAction.send(FavouriteAction.OpenPopupToDeleteQuestion(question))
        }
    }

    fun onDeleteQuestion(question: DialectQuestion, onSuccess: () -> Unit) {
        Log.d(TAG, "OnDeleteQuestion")
        viewModelScope.launch (Dispatchers.Main) {
            deleteFavouriteUseCase.invoke(question)
            updateFavourites()
            onSuccess()
        }
    }

    fun updateFavourites() {
        Log.d(TAG, "getFavQuestions")
        viewModelScope.launch(Dispatchers.Main) {
            val favourites = getFavouritesUseCase.invoke()
            _uiState.update {
                it.copy(
                    questions = favourites
                )
            }
        }
    }
}

data class FavouriteUiState(
    val isInit: Boolean = false,
    val questions: List<DialectQuestion> = emptyList()
)

sealed class FavouriteAction {
    data class OpenPopupToDeleteQuestion(val question: DialectQuestion): FavouriteAction()
}
