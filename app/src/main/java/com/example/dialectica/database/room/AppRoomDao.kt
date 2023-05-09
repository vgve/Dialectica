package com.example.dialectica.database.room

import androidx.room.*
import com.example.dialectica.models.DialectInterest
import com.example.dialectica.models.DialectPerson
import com.example.dialectica.models.DialectQuestion

@Dao
interface AppRoomDao {

    @Query("SELECT *from question_tables")
    fun getFavouriteList(): List<DialectQuestion>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(question: DialectQuestion?)

    @Delete
    suspend fun deleteFavourite(question: DialectQuestion?)

    @Query("SELECT *from person_tables")
    fun getPersonList(): List<DialectPerson>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPerson(person: DialectPerson?)

    @Query("UPDATE person_tables SET interests=:interests WHERE id = :id")
    suspend fun updatePerson(interests: List<String>, id: Int?)

    @Delete
    suspend fun deletePerson(person: DialectPerson?)

    @Query("SELECT *from interest_tables")
    fun getInterestList(): List<DialectInterest>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInterest(interest: DialectInterest?)

    @Delete
    suspend fun deleteInterest(interest: DialectInterest?)
}
