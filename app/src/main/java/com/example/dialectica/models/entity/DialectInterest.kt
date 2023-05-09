package com.example.dialectica.models.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "interest_tables")
data class DialectInterest (
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    @ColumnInfo val name: String,
    @ColumnInfo val owner: String
) : Serializable
