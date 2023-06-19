package com.example.dialectica.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.database.room.AppRoomRepository
import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.data.models.DialectTheme
import com.example.dialectica.data.models.Themes
import com.example.dialectica.data.models.entity.DialectPerson
import com.example.dialectica.utils.REPOSITORY
import com.example.dialectica.utils.TAG
import com.example.dialectica.utils.TYPE_ROOM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val repository: AppRoomRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiAction: Channel<HomeAction> = Channel()
    val uiAction = _uiAction.receiveAsFlow()

    init {
        _uiState.update {
            it.copy(
                themeList = Themes().themeList,
                allQuestions = Themes().questionList
            )
        }
    }

    fun initDatabase(type: String, onSuccess: () -> Unit) {
        when(type) {
            TYPE_ROOM -> {
                // callback about completed
                onSuccess()
            }
        }
    }

    fun onClickTheme(theme: DialectTheme) {
        Log.d(this.TAG, "onClickTheme")
        val themes = _uiState.value.themeList.map {
            it.isChosen = it.id == theme.id
            it
        }
        val questions = mutableListOf<DialectQuestion>()
        Themes().questionList.forEach {
            if (it.idTheme == theme.id) questions.add(it)
        }
        val newQuestion = questions.random()

        _uiState.update {
            it.copy(
                themeList = themes,
                currentQuestionList = questions,
                currentQuestion = newQuestion,
                isFavourite = checkFavourite(newQuestion)
            )
        }
    }

    fun onClickNext() {
        Log.d(TAG, "onClickNext")
        val currentIndex = _uiState.value.currentQuestionList.indexOf(_uiState.value.currentQuestion)
        val nextQuestion = if (currentIndex + 1 < _uiState.value.currentQuestionList.size) {
            _uiState.value.currentQuestionList[currentIndex + 1]
        } else {
            _uiState.value.currentQuestionList.first()
        }

        _uiState.update {
            it.copy(
                currentQuestion = nextQuestion,
                isFavourite = checkFavourite(nextQuestion)
            )
        }
    }

    fun onClickRandom(): DialectQuestion? {
        var randomQuestion = _uiState.value.currentRandomQuestion
        while (randomQuestion == _uiState.value.currentRandomQuestion || randomQuestion == _uiState.value.currentQuestion) {
            randomQuestion = _uiState.value.allQuestions.random()
        }
        _uiState.update {
            it.copy(
                isRandom = true,
                currentRandomQuestion = randomQuestion
            )
        }
        return randomQuestion
    }

    fun addToFavourite(question: DialectQuestion?, onSuccess: () -> Unit) {
        Log.d(TAG, "addToFavourite")
        if (checkFavourite(question)) return

        _uiState.update { it.copy(isFavourite = true, isRandom = false) }

        viewModelScope.launch(Dispatchers.Main) {
            repository.insertFavourite(question)
            getFavQuestions()
            onSuccess()
        }
    }

    fun deleteFavourite(question: DialectQuestion?, onSuccess: () -> Unit) {
        _uiState.update { it.copy(isFavourite = false, isRandom = false) }

        viewModelScope.launch(Dispatchers.Main) {
            repository.deleteFavourite(question)
            getFavQuestions()
            onSuccess()
        }
    }

    private fun checkFavourite(question: DialectQuestion?): Boolean {
        Log.d(TAG, "checkFavourite: $question")
        var isFavourite = false
        _uiState.value.favouriteList.forEach {
            if (it.text == question?.text) isFavourite = true
        }
        return isFavourite
    }

    fun changeFavouriteState() {
        _uiState.update {
            it.copy(
                isFavourite = checkFavourite(_uiState.value.currentQuestion)
            )
        }
    }

    fun getFavQuestions() {
        Log.d(TAG, "getFavQuestions")
        viewModelScope.launch(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    favouriteList = REPOSITORY.getFavouriteList()
                )
            }
        }
    }

    fun getPersons() {
        Log.d(TAG, "getPersons")
        viewModelScope.launch(Dispatchers.Main) {
            val tempPersons = repository.getPersonList()
            val personsWithoutOwner = tempPersons.toMutableList()
            tempPersons.forEach {
                if (it.isOwner) {
                    personsWithoutOwner.remove(it)
                }
            }
            _uiState.update {
                it.copy(
                    personList = personsWithoutOwner
                )
            }
        }
    }

    fun addQuestionToPerson(person: DialectPerson, onSuccess: () -> Unit) {
        Log.d(TAG, "addQuestionToPerson: ${person.name}")
        val question = if (_uiState.value.isRandom) _uiState.value.currentRandomQuestion else _uiState.value.currentQuestion

        viewModelScope.launch(Dispatchers.Main) {
            val newQuestionList = repository.getPersonById(person.id).questions.toMutableList()
            if (!newQuestionList.contains(question)) {
                question?.let { newQuestionList.add(it) }
                repository.updatePersonQuestions(newQuestionList, person.id)
            }
            _uiAction.send(HomeAction.AddQuestionToPersonClick)
            onSuccess()
        }
    }

    fun setRandomState(isRandom: Boolean) {
        Log.d(TAG, "setRandomState: $isRandom")
        _uiState.update { it.copy(isRandom = isRandom) }
    }
}

data class HomeUiState(
    val themeList: List<DialectTheme> = emptyList(),
    val allQuestions: List<DialectQuestion> = emptyList(),
    val currentQuestionList: List<DialectQuestion> = emptyList(),
    val favouriteList: List<DialectQuestion> = emptyList(),
    val currentQuestion: DialectQuestion? = null,
    val currentRandomQuestion: DialectQuestion? = null,
    val personList: List<DialectPerson> = emptyList(),
    val isFavourite: Boolean = false,
    val isRandom: Boolean = false
)

sealed class HomeAction {
    object AddQuestionToPersonClick : HomeAction()
}
