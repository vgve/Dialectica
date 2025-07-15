package com.vicgcode.dialectica.core.domain.repositories

interface SharedPrefsRepository {

    fun setAuthorize(init: Boolean)

    fun setUsername(name: String?)

    fun setTypeDatabase(type: String)

    fun isAuthorize(): Boolean

    fun getUserName(): String

    fun getUserAuthorize(): Boolean

    fun getTypeDatabase(): String
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
}
