package com.example.dialectica.domain.usecases

import com.example.dialectica.database.room.AppRoomRepository

class GetPersonsUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke() =
        appRoomRepository.getPersonList()
}
