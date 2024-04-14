package com.example.dialectica.core.domain.repositories

interface SharedPrefsRepository {

    fun setInitUser(init: Boolean)

    fun setUserName(name: String?)

    fun setTypeDatabase(type: String)

    fun getInitUser(): Boolean

    fun getUserName(): String

    fun getUserAuthorize(): Boolean

    fun getTypeDatabase(): String
}
