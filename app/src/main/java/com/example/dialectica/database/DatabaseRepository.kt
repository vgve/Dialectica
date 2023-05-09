package com.example.dialectica.database

import com.example.dialectica.models.DialectInterest
import com.example.dialectica.models.DialectPerson
import com.example.dialectica.models.DialectQuestion

interface DatabaseRepository {
    val favQuestions: List<DialectQuestion>
    val personList: List<DialectPerson>
    val interestList: List<DialectInterest>

    suspend fun insertFavourite(question: DialectQuestion?)

    suspend fun deleteFavourite(question: DialectQuestion?)

    suspend fun getFavouriteList() : List<DialectQuestion>

    suspend fun insertPerson(person: DialectPerson?)

    suspend fun updatePerson(interests: List<String>, id: Int?)

    suspend fun deletePerson(person: DialectPerson?)

    suspend fun getPersonList() : List<DialectPerson>

    suspend fun insertInterest(interest: DialectInterest?)

    suspend fun deleteInterest(interest: DialectInterest?)

    suspend fun getInterestList() : List<DialectInterest>
}
