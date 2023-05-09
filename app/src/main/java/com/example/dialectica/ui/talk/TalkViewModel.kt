package com.example.dialectica.ui.talk

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dialectica.models.entity.DialectInterest
import com.example.dialectica.models.entity.DialectPerson
import com.example.dialectica.models.entity.DialectQuestion
import com.example.dialectica.utils.REPOSITORY
import com.example.dialectica.utils.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TalkViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(TalkUiState())
    val uiState = _uiState.asStateFlow()

    fun addInterest(interest: String) {
        Log.d(TAG, "addInterest")
        if (interest.isEmpty()) return
        val updatedInterests = _uiState.value.simpleInterestList.toMutableList()
        updatedInterests.add(interest)
        _uiState.update { it.copy(simpleInterestList = updatedInterests) }
        setLocalInterestList(_uiState.value.simpleInterestList)

        updatePerson {  }
    }

    private fun updatePerson(onSuccess: () -> Unit) {
        Log.d(TAG, "updateOwnPerson")

        viewModelScope.launch(Dispatchers.Main) {
            REPOSITORY.updatePersonInterests(_uiState.value.simpleInterestList, _uiState.value.personId)
            onSuccess()
        }
    }

    fun onDeleteInterest(interest: LocalInterest) {
        Log.d(TAG, "onDeleteInterest")
        val updatedInterests = _uiState.value.simpleInterestList.toMutableList()
        updatedInterests.remove(interest.name)

        val updatedLocalInterests = _uiState.value.interestList.toMutableList()
        updatedLocalInterests.remove(interest)

        _uiState.update {
            it.copy(
                simpleInterestList = updatedInterests,
                interestList = updatedLocalInterests
            )
        }

        updatePerson {  }
    }

    fun setPerson(dialectPerson: DialectPerson) {
        Log.d(TAG, "setPerson")
        getOwnerPerson {
            setLocalInterestList(dialectPerson.interests)
        }
        _uiState.update {
            it.copy(
                username = dialectPerson.name,
                personId = dialectPerson.id,
                isOwner = dialectPerson.isOwner,
                simpleInterestList = dialectPerson.interests,
                questionList = dialectPerson.questions
            )
        }
    }

    private fun setLocalInterestList(interests: List<String>) {
        Log.d(TAG, "setLocalInterestList: field isCommon")
        val localInterestList = mutableListOf<LocalInterest>()
        interests.forEach {
            localInterestList.add(
                LocalInterest(
                    name = it,
                    isCommon = _uiState.value.ownerInterestList.contains(it)
                )
            )
        }

        _uiState.update { it.copy(interestList = localInterestList) }
    }

    private fun getOwnerPerson(onSuccess: () -> Unit) {
        Log.d(TAG, "getOwnerPerson")

        viewModelScope.launch(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    ownerInterestList = REPOSITORY.getOwnerPerson(true).interests
                )
            }
            onSuccess()
        }
    }

    fun addNewQuestion(question: String, onSuccess: () -> Unit) {
        Log.d(TAG, "addNewQuestion")
        val newQuestionList = _uiState.value.questionList.toMutableList()
        newQuestionList.add(
            DialectQuestion(
                text = question,
                idTheme = "own"
            )
        )
        _uiState.update { it.copy(questionList = newQuestionList) }

        viewModelScope.launch(Dispatchers.Main) {
            REPOSITORY.updatePersonQuestions(newQuestionList, _uiState.value.personId)
            onSuccess()
        }
    }

    fun onDeleteQuestion(question: DialectQuestion, onSuccess: () -> Unit) {
        Log.d(TAG, "onDeleteQuestion")
        val newQuestionList = _uiState.value.questionList.toMutableList()
        newQuestionList.remove(question)

        _uiState.update { it.copy(questionList = newQuestionList) }

        viewModelScope.launch(Dispatchers.Main) {
            REPOSITORY.updatePersonQuestions(newQuestionList, _uiState.value.personId)
            onSuccess()
        }
    }

    fun getRandom(): DialectQuestion? {
        var randomQuestion = _uiState.value.currentRandomQuestion
        while (randomQuestion == _uiState.value.currentRandomQuestion) {
            randomQuestion = _uiState.value.questionList.random()
        }
        _uiState.update {
            it.copy(
                currentRandomQuestion = randomQuestion
            )
        }
        return randomQuestion
    }
}

data class TalkUiState(
    val username: String? = null,
    val personId: Int? = 0,
    val isOwner: Boolean = false,
    val simpleInterestList: List<String> = emptyList(),
    val interestList: List<LocalInterest> = emptyList(),
    val ownerInterestList: List<String> = emptyList(),
    val questionList: List<DialectQuestion> = emptyList(),
    val currentRandomQuestion: DialectQuestion? = null
)

data class LocalInterest(
    val name: String? = null,
    val isCommon: Boolean = false
)

