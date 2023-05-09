package com.example.dialectica.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "question_tables")
data class DialectQuestion (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val idTheme: String,
    @ColumnInfo val text: String,
    @ColumnInfo var isFavourite: Boolean? = false
) : Serializable
