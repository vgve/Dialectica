package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository

class GetFavouritesUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke() =
        appRoomRepository.getFavouriteList()
}
