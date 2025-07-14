package com.example.dialectica.domain.usecases

import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.database.room.AppRoomRepository

class UpdatePersonQuestionsUseCase(
    private val appRoomRepository: AppRoomRepository
) {
    suspend operator fun invoke(questions: List<DialectQuestion>, id: Int) =
        appRoomRepository.updatePersonQuestions(questions, id)
}
