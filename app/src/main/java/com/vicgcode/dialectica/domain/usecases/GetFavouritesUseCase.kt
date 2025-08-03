package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.database.room.AppRoomRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke() =
        appRoomRepository.getFavouriteList()
}
