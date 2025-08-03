package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.database.room.AppRoomRepository
import javax.inject.Inject

class DeletePersonUseCase @Inject constructor(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(person: DialectPerson) =
        appRoomRepository.deletePerson(person)
}
