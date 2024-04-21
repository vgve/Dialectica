package com.example.dialectica.core.domain.repositories

interface SharedPrefsRepository {

    fun setAuthorize(init: Boolean)

    fun setUserName(name: String?)

    fun setTypeDatabase(type: String)

    fun isAuthorize(): Boolean

    fun getUserName(): String

    fun getUserAuthorize(): Boolean

    fun getTypeDatabase(): String
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
}
