package com.example.dialectica.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.dialectica.models.DialectQuestion

@Dao
interface AppRoomDao {
    @Query("SELECT *from question_tables")
    fun getFavQuestions(): LiveData<List<DialectQuestion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(question: DialectQuestion?)

    @Delete
    suspend fun delete(question: DialectQuestion?)
}
