package com.example.dialectica.domain.usecases

import com.example.dialectica.database.room.AppRoomRepository

class GetPersonByIdUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(id: Int?) =
        appRoomRepository.getPersonById(id)
}
