package com.example.dialectica.models

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
    @ColumnInfo val isOwner: Boolean
) : Serializable
