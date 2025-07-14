package com.example.dialectica.domain.usecases

import com.example.dialectica.data.models.entity.DialectPerson
import com.example.dialectica.database.room.AppRoomRepository

class DeletePersonUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(person: DialectPerson) =
        appRoomRepository.deletePerson(person)
}
