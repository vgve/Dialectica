package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository

class UpdatePersonInterestsUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(interests: List<String>, id: Int?) =
        appRoomRepository.updatePersonInterests(interests, id)
}
