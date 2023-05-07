package com.example.dialectica.database

import androidx.lifecycle.LiveData
import com.example.dialectica.models.DialectQuestion

interface DatabaseRepository {
    val favQuestions: LiveData<List<DialectQuestion>>
    suspend fun insert(note: DialectQuestion?, onSuccess: () -> Unit)
    suspend fun delete(note: DialectQuestion?, onSuccess: () -> Unit)
}
