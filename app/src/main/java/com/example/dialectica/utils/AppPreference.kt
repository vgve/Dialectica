package com.example.dialectica.utils

import android.content.SharedPreferences

object AppPreference {

    private const val INIT_USER = "initUser"
    private const val USER_NAME = "userName"
    private const val TYPE_DATABASE = "typeDatabase"
    private const val NAME_PREF = "user_preference"

    private lateinit var sharedPrefs: SharedPreferences

    fun setInitUser(init: Boolean) {
        sharedPrefs.edit().putBoolean(INIT_USER, init).apply()
    }

    fun setUserName(name: String) {
        sharedPrefs.edit().putString(USER_NAME, name).apply()
    }

    fun setTypeDatabase(type: String) {
        sharedPrefs.edit().putString(TYPE_DATABASE, type).apply()
    }

    fun getInitUser(): Boolean {
        return sharedPrefs.getBoolean(INIT_USER, false)
    }

    fun getUserName(): String {
        return sharedPrefs.getString(USER_NAME, USER_QUEST).toString()
    }

    fun getUserAuthorize(): Boolean {
        return sharedPrefs.getString(USER_NAME, USER_QUEST).toString() != USER_QUEST
    }

    fun getTypeDatabase(): String {
        return sharedPrefs.getString(TYPE_DATABASE, TYPE_ROOM).toString()
    }
}
