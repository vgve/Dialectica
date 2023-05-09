package com.example.dialectica.utils

import android.content.Context
import android.content.SharedPreferences

object AppPreference {

    private const val INIT_USER = "initUser"
    private const val USER_NAME = "userName"
    private const val TYPE_DATABASE = "typeDatabase"
    private const val NAME_PREF = "user_preference"

    private lateinit var mPreferences: SharedPreferences

    fun getPreference(context: Context): SharedPreferences {
        mPreferences = context.getSharedPreferences(NAME_PREF, Context.MODE_PRIVATE)
        return mPreferences
    }

    fun setInitUser(init: Boolean) {
        mPreferences.edit().putBoolean(INIT_USER, init).apply()
    }

    fun setUserName(name: String) {
        mPreferences.edit().putString(USER_NAME, name).apply()
    }

    fun setTypeDatabase(type: String) {
        mPreferences.edit().putString(TYPE_DATABASE, type).apply()
    }

    fun getInitUser(): Boolean {
        return mPreferences.getBoolean(INIT_USER, false)
    }

    fun getUserName(): String {
        return mPreferences.getString(USER_NAME, USER_QUEST).toString()
    }

    fun getUserAuthorize(): Boolean {
        return mPreferences.getString(USER_NAME, USER_QUEST).toString() != USER_QUEST
    }

    fun getTypeDatabase(): String {
        return mPreferences.getString(TYPE_DATABASE, TYPE_ROOM).toString()
    }
}
