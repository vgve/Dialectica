package com.example.dialectica.domain.usecases

import com.example.dialectica.data.models.entity.DialectInterest
import com.example.dialectica.database.room.AppRoomRepository

class AddInterestUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(interest: DialectInterest?) =
        appRoomRepository.insertInterest(interest)
}
