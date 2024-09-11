package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository

class GetOwnPersonUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(isOwner: Boolean) =
        appRoomRepository.getOwnerPerson(isOwner)
}
