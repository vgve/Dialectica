package com.vicgcode.dialectica.domain.usecases

import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.database.room.AppRoomRepository
import javax.inject.Inject

class UpdatePersonQuestionsUseCase @Inject constructor(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(questions: List<DialectQuestion>, id: Int) =
        appRoomRepository.updatePersonQuestions(questions, id)
}
