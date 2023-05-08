package com.example.dialectica.models

data class DialectTheme(
    val id: String,
    val name: String,
    val src: Int,
    var isChosen: Boolean? = false
)
