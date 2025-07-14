package com.example.dialectica.domain.usecases

import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.database.room.AppRoomRepository

class AddFavouriteUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(question: DialectQuestion) =
        appRoomRepository.insertFavourite(question)
}
