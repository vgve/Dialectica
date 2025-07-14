package com.example.dialectica.domain.usecases

import com.example.dialectica.database.room.AppRoomRepository

class UpdatePersonInterestsUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(interests: List<String>, id: Int) =
        appRoomRepository.updatePersonInterests(interests, id)
}
