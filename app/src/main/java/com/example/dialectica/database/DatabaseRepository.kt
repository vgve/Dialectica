package com.example.dialectica.database

import com.example.dialectica.data.models.entity.DialectInterest
import com.example.dialectica.data.models.entity.DialectPerson
import com.example.dialectica.data.models.entity.DialectQuestion

interface DatabaseRepository {
    val favQuestions: List<DialectQuestion>
    val personList: List<DialectPerson>
    val interestList: List<DialectInterest>

    suspend fun insertFavourite(question: DialectQuestion?)

    suspend fun deleteFavourite(question: DialectQuestion?)

    suspend fun getFavouriteList() : List<DialectQuestion>

    suspend fun insertPerson(person: DialectPerson?)

    suspend fun updatePersonInterests(interests: List<String>, id: Int?)

    suspend fun updatePersonQuestions(questions: List<DialectQuestion>, id: Int?)

    suspend fun deletePerson(person: DialectPerson?)

    suspend fun getOwnerPerson(isOwner: Boolean?) : DialectPerson?

    suspend fun getPersonById(id: Int?) : DialectPerson?

    suspend fun getPersonList() : List<DialectPerson>

    suspend fun insertInterest(interest: DialectInterest?)

    suspend fun deleteInterest(interest: DialectInterest?)

    suspend fun getInterestList() : List<DialectInterest>
}
