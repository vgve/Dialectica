package com.example.dialectica.database

import com.example.dialectica.models.DialectQuestion

interface DatabaseRepository {
    val favQuestions: List<DialectQuestion>

    suspend fun insert(note: DialectQuestion?)

    suspend fun delete(note: DialectQuestion?)

    suspend fun getFavQuestions() : List<DialectQuestion>
}
