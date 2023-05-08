package com.example.dialectica.database.room

import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.database.DatabaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRoomRepository(private val appRoomDao: AppRoomDao) : DatabaseRepository {

    override val favQuestions: List<DialectQuestion>
        get() = appRoomDao.getFavQuestions()

    override suspend fun getFavQuestions(): List<DialectQuestion> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getFavQuestions()
        }
    }

    override suspend fun insert(question: DialectQuestion?) {
        withContext(Dispatchers.IO) {
            appRoomDao.insert(question)
        }
    }

    override suspend fun delete(question: DialectQuestion?) {
        withContext(Dispatchers.IO) {
            appRoomDao.delete(question)
        }
    }
}
