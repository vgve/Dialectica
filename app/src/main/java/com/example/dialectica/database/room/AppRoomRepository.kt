package com.example.dialectica.database.room

import com.example.dialectica.data.models.entity.DialectQuestion
import com.example.dialectica.database.DatabaseRepository
import com.example.dialectica.data.models.entity.DialectInterest
import com.example.dialectica.data.models.entity.DialectPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRoomRepository(private val appRoomDao: AppRoomDao) : DatabaseRepository {

    override suspend fun getFavouriteList(): List<DialectQuestion> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getFavouriteList()
        }
    }

    override suspend fun insertFavourite(question: DialectQuestion) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertFavourite(question)
        }
    }

    override suspend fun deleteFavourite(question: DialectQuestion) {
        withContext(Dispatchers.IO) {
            appRoomDao.deleteFavourite(question)
        }
    }

    override suspend fun insertPerson(person: DialectPerson) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertPerson(person)
        }
    }

    override suspend fun updatePersonInterests(interests: List<String>, id: Int) {
        withContext(Dispatchers.IO) {
            appRoomDao.updatePersonInterests(interests, id)
        }
    }

    override suspend fun updatePersonQuestions(questions: List<DialectQuestion>, id: Int) {
        withContext(Dispatchers.IO) {
            appRoomDao.updatePersonQuestions(questions, id)
        }
    }

    override suspend fun deletePerson(person: DialectPerson) {
        withContext(Dispatchers.IO) {
            appRoomDao.deletePerson(person)
        }
    }

    override suspend fun getOwnerPerson(isOwner: Boolean): DialectPerson {
        return withContext(Dispatchers.IO) {
            appRoomDao.getOwnerPerson(isOwner)
        }
    }

    override suspend fun getPersonById(id: Int): DialectPerson {
        return withContext(Dispatchers.IO) {
            appRoomDao.getPersonById(id)
        }
    }

    override suspend fun getPersonList(): List<DialectPerson> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getPersonList()
        }
    }

    override suspend fun insertInterest(interest: DialectInterest) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertInterest(interest)
        }
    }

    override suspend fun deleteInterest(interest: DialectInterest) {
        withContext(Dispatchers.IO) {
            appRoomDao.deleteInterest(interest)
        }
    }

    override suspend fun getInterestList(): List<DialectInterest> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getInterestList()
        }
    }
}
