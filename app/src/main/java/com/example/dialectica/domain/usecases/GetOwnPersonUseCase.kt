package com.example.dialectica.domain.usecases

import com.example.dialectica.database.room.AppRoomRepository

class GetOwnPersonUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(isOwner: Boolean) =
        appRoomRepository.getOwnerPerson(isOwner)
}
