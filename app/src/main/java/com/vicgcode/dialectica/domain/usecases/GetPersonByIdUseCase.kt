package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository

class GetPersonByIdUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(id: Int) =
        appRoomRepository.getPersonById(id)
}
