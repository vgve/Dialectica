package com.vicgcode.dialectica.presentation.personal

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.database.room.AppRoomRepository
import com.vicgcode.dialectica.presentation.extensions.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalViewModel(
    private val sharedPrefsRepository: SharedPrefsRepository,
    private val appRoomRepository: AppRoomRepository
): ViewModel() {

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
            val persons = appRoomRepository.getPersonList()
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

        updateOwnPerson { }
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

        updateOwnPerson { }
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
            appRoomRepository.insertPerson(newPerson)
            getPersons()
            onSuccess()
        }
    }

    private fun updateOwnPerson(onSuccess: () -> Unit) {
        Log.d(TAG, "updateOwnPerson")

        viewModelScope.launch(Dispatchers.Main) {
            appRoomRepository.updatePersonInterests(_uiState.value.ownInterestList, _uiState.value.ownerId)
            getPersons()
            onSuccess()
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
            appRoomRepository.insertPerson(newPerson)
            getPersons()
            sharedPrefsRepository.setUserName(username)
        }
    }

    fun onDeletePerson(person: DialectPerson, onSuccess: () -> Unit) {
        Log.d(TAG, "onDeletePerson")
        viewModelScope.launch(Dispatchers.Main) {
            appRoomRepository.deletePerson(person)
            getPersons()
            onSuccess()
        }
    }
}

data class PersonalUiState(
    val username: String? = null,
    val ownerId: Int? = 0,
    val ownInterestList: List<String> = emptyList(),
    val tempInterestList: List<String> = emptyList(),
    val isAuthorized: Boolean = false,
    val personList: List<DialectPerson> = emptyList()
)
