package com.vicgcode.dialectica.presentation.screens.contacts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.domain.usecases.AddPersonUseCase
import com.vicgcode.dialectica.domain.usecases.DeletePersonUseCase
import com.vicgcode.dialectica.domain.usecases.GetPersonsUseCase
import com.vicgcode.dialectica.domain.usecases.UpdatePersonInterestsUseCase
import com.vicgcode.dialectica.presentation.extensions.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val getPersonsUseCase: GetPersonsUseCase,
    private val addPersonUseCase: AddPersonUseCase,
    private val updatePersonInterestsUseCase: UpdatePersonInterestsUseCase,
    private val deletePersonUseCase: DeletePersonUseCase
): ViewModel() {

    companion object {
        const val PERSON_ID = "person_id"
    }

    private val _uiState = MutableStateFlow(PersonalUiState())
    val uiState = _uiState.asStateFlow()

    init {
        if (sharedPrefsRepository.getUserAuthorize()) {
            setUsername(sharedPrefsRepository.getUserName())
        }
    }

    fun getPersons() {
        Log.d(TAG, "getPersons")
        viewModelScope.launch(Dispatchers.Main) {
            val persons = getPersonsUseCase.invoke()
            _uiState.update {
                it.copy(
                    personList = persons,
                    ownInterestList = persons.find { it.isOwner }?.interests.orEmpty(),
                    ownerId = persons.find { it.isOwner }?.id,
                )
            }
        }
    }

    fun addOwnInterest(interest: String) {
        Log.d(TAG, "addInterestOfUser")
        if (interest.isEmpty()) return
        val updatedInterests = _uiState.value.ownInterestList.toMutableList()
        updatedInterests.add(interest)
        _uiState.update { it.copy(ownInterestList = updatedInterests) }

        updateOwnPerson()
    }

    fun addInterestOfUser(interest: String) {
        Log.d(TAG, "addInterestOfUser")
        if (interest.isEmpty()) return
        val updatedInterests = _uiState.value.tempInterestList.toMutableList()
        updatedInterests.add(interest)
        _uiState.update { it.copy(tempInterestList = updatedInterests) }
    }

    fun onDeleteOwnInterest(interest: String) {
        Log.d(TAG, "onDeleteInterest")
        val updatedInterests = _uiState.value.ownInterestList.toMutableList()
        updatedInterests.remove(interest)

        _uiState.update { it.copy(ownInterestList = updatedInterests) }

        updateOwnPerson()
    }

    fun onDeleteInterestOfUser(interest: String) {
        Log.d(TAG, "onDeleteInterest")
        val updatedInterests = _uiState.value.tempInterestList.toMutableList()
        updatedInterests.remove(interest)

        _uiState.update { it.copy(tempInterestList = updatedInterests) }
    }

    private fun setUsername(username: String) {
        Log.d(TAG, "saveUsername $username")

        _uiState.update {
            it.copy(
                username = username,
                isAuthorized = true
            )
        }
    }

    fun insertPerson(personName: String, onSuccess: () -> Unit) {
        Log.d(TAG, "insertPerson")
        val newPerson = DialectPerson(
            name = personName,
            interests = _uiState.value.tempInterestList,
            isOwner = false,
            questions = emptyList()
        )

        _uiState.update { it.copy(tempInterestList = emptyList()) }

        viewModelScope.launch(Dispatchers.Main) {
            addPersonUseCase.invoke(newPerson)
            getPersons()
            onSuccess()
        }
    }

    private fun updateOwnPerson() {
        Log.d(TAG, "updateOwnPerson")

        viewModelScope.launch(Dispatchers.Main) {
            try {
                _uiState.value.ownerId?.let {
                    updatePersonInterestsUseCase.invoke(_uiState.value.ownInterestList, it)
                    getPersons()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
                _uiState.update { it.copy(isFailed = true) }
            }
        }
    }

    fun loginUser(username: String) {
        Log.d(TAG, "loginUser")
        setUsername(username)
        val newPerson = DialectPerson(
            name = username,
            interests = _uiState.value.ownInterestList,
            isOwner = true,
            questions = emptyList()
        )

        viewModelScope.launch(Dispatchers.Main) {
            addPersonUseCase.invoke(newPerson)
            getPersons()
            sharedPrefsRepository.setUsername(username)
        }
    }

    fun onDeletePerson(person: DialectPerson, onSuccess: () -> Unit) {
        Log.d(TAG, "onDeletePerson")
        viewModelScope.launch(Dispatchers.Main) {
            deletePersonUseCase.invoke(person)
            getPersons()
            onSuccess()
        }
    }
}

data class PersonalUiState(
    val username: String? = null,
    val isFailed: Boolean = false,
    val ownerId: Int? = 0,
    val ownInterestList: List<String> = emptyList(),
    val tempInterestList: List<String> = emptyList(),
    val isAuthorized: Boolean = false,
    val personList: List<DialectPerson> = emptyList()
)
