package com.example.dialectica.data

data class DialectQuestion (
    val id: String,
    val idTheme: String,
    val textQuestion: String,
    var isChosen: Boolean? = false,
)
