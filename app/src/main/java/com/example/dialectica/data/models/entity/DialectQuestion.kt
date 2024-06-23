package com.example.dialectica.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "question_tables")
data class DialectQuestion(
    @ColumnInfo val idTheme: String,
    @ColumnInfo val text: String,
    @ColumnInfo var isFavourite: Boolean? = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
