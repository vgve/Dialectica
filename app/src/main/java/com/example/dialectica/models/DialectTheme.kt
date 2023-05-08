package com.example.dialectica.models

data class DialectTheme(
    val id: String,
    val nameTheme: String,
    val srcTheme: Int,
    var isChosen: Boolean? = false
)
