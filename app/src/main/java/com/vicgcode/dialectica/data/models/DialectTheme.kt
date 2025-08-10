package com.vicgcode.dialectica.data.models

import androidx.annotation.DrawableRes

data class DialectTheme(
    val id: String,
    val name: String,
    @DrawableRes val src: Int,
    var isChosen: Boolean? = false
)
