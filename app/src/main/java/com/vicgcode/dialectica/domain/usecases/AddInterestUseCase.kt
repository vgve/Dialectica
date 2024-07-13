package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.data.models.entity.DialectInterest
import com.vicgcode.dialectica.database.room.AppRoomRepository

class AddInterestUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(interest: DialectInterest?) =
        appRoomRepository.insertInterest(interest)
}
