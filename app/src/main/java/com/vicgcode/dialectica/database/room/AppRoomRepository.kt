package com.vicgcode.dialectica.database.room

import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.database.DatabaseRepository
import com.vicgcode.dialectica.data.models.entity.DialectInterest
import com.vicgcode.dialectica.data.models.entity.DialectPerson
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
            question?.let { appRoomDao.insertFavourite(question) }
        }
    }

    override suspend fun deleteFavourite(question: DialectQuestion?) {
        withContext(Dispatchers.IO) {
            question?.let { appRoomDao.deleteFavourite(question) }
        }
    }

    override suspend fun insertPerson(person: DialectPerson?) {
        withContext(Dispatchers.IO) {
            person?.let { appRoomDao.insertPerson(person) }
        }
    }

    override suspend fun updatePersonInterests(interests: List<String>, id: Int?) {
        withContext(Dispatchers.IO) {
            id?.let { appRoomDao.updatePersonInterests(interests, id) }
        }
    }

    override suspend fun updatePersonQuestions(questions: List<DialectQuestion>, id: Int?) {
        withContext(Dispatchers.IO) {
            id?.let { appRoomDao.updatePersonQuestions(questions, id) }
        }
    }

    override suspend fun deletePerson(person: DialectPerson?) {
        withContext(Dispatchers.IO) {
            person?.let { appRoomDao.deletePerson(person) }
        }
    }

    override suspend fun getOwnerPerson(isOwner: Boolean?): DialectPerson? {
        return withContext(Dispatchers.IO) {
            isOwner?.let { appRoomDao.getOwnerPerson(isOwner) }
        }
    }

    override suspend fun getPersonById(id: Int?): DialectPerson? {
        return withContext(Dispatchers.IO) {
            id?.let { appRoomDao.getPersonById(id) }
        }
    }

    override suspend fun getPersonList(): List<DialectPerson> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getPersonList()
        }
    }

    override suspend fun insertInterest(interest: DialectInterest?) {
        withContext(Dispatchers.IO) {
            interest?.let { appRoomDao.insertInterest(interest) }
        }
    }

    override suspend fun deleteInterest(interest: DialectInterest?) {
        withContext(Dispatchers.IO) {
            interest?.let { appRoomDao.deleteInterest(interest) }
        }
    }

    override suspend fun getInterestList(): List<DialectInterest> {
        return withContext(Dispatchers.IO) {
            appRoomDao.getInterestList()
        }
    }
}
