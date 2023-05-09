package com.example.dialectica.database.room

import com.example.dialectica.models.DialectQuestion
import com.example.dialectica.database.DatabaseRepository
import com.example.dialectica.models.DialectInterest
import com.example.dialectica.models.DialectPerson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRoomRepository(private val appRoomDao: AppRoomDao) : DatabaseRepository {

    override val favQuestions: List<DialectQuestion>
        get() = appRoomDao.getFavouriteList()
    override val personList: List<DialectPerson>
        get() = appRoomDao.getPersonList()
    override val interestList: List<DialectInterest>
        get() = appRoomDao.getInterestList()

    override suspend fun getFavouriteList(): List<DialectQuestion> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getFavouriteList()
        }
    }

    override suspend fun insertFavourite(question: DialectQuestion?) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertFavourite(question)
        }
    }

    override suspend fun deleteFavourite(question: DialectQuestion?) {
        withContext(Dispatchers.IO) {
            appRoomDao.deleteFavourite(question)
        }
    }

    override suspend fun insertPerson(person: DialectPerson?) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertPerson(person)
        }
    }

    override suspend fun updatePerson(interests: List<String>, id: Int?) {
        withContext(Dispatchers.IO) {
            appRoomDao.updatePerson(interests, id)
        }
    }

    override suspend fun deletePerson(person: DialectPerson?) {
        withContext(Dispatchers.IO) {
            appRoomDao.deletePerson(person)
        }
    }

    override suspend fun getPersonList(): List<DialectPerson> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getPersonList()
        }
    }

    override suspend fun insertInterest(interest: DialectInterest?) {
        withContext(Dispatchers.IO) {
            appRoomDao.insertInterest(interest)
        }
    }

    override suspend fun deleteInterest(interest: DialectInterest?) {
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
