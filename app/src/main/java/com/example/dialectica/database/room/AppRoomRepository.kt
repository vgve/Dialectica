package com.example.dialectica.database.room

import androidx.lifecycle.LiveData
import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.database.DatabaseRepository

class AppRoomRepository(private val appRoomDao: AppRoomDao) : DatabaseRepository {
    override val favQuestions: LiveData<List<DialectQuestion>>
        get() = appRoomDao.getFavQuestions()

    override suspend fun insert(question: DialectQuestion?, onSuccess: () -> Unit) {
        appRoomDao.insert(question)
        onSuccess()
    }

    override suspend fun delete(question: DialectQuestion?, onSuccess: () -> Unit) {
        appRoomDao.delete(question)
        onSuccess()
    }

}
