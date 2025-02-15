package com.vicgcode.dialectica.data.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable

@Entity(tableName = "person_tables")
data class DialectPerson (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val name: String,
    @TypeConverters(DataConverter::class)
    @ColumnInfo val interests: List<String>,
    @TypeConverters(QuestionConverter::class)
    @ColumnInfo val questions: List<DialectQuestion>,
    @ColumnInfo val isOwner: Boolean,
)
