package com.example.dialectica.domain.usecases

import com.example.dialectica.database.room.AppRoomRepository

class GetFavouritesUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke() =
        appRoomRepository.getFavouriteList()
}
