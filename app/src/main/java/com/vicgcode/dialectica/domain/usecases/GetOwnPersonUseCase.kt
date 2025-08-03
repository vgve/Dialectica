package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository
import javax.inject.Inject

class GetOwnPersonUseCase @Inject constructor(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(isOwner: Boolean) =
        appRoomRepository.getOwnerPerson(isOwner)
}
