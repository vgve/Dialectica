package com.example.dialectica.data

data class DialectTheme(
    val id: String,
    val nameTheme: String,
    val srcTheme: Int,
    var isChosen: Boolean? = false
)
