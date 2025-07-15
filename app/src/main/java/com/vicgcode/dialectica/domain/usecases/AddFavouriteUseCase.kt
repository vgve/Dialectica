package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.database.room.AppRoomRepository

class AddFavouriteUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(question: DialectQuestion) =
        appRoomRepository.insertFavourite(question)
}
