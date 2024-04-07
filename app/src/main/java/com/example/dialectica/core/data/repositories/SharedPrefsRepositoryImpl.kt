package com.example.dialectica.core.data.repositories

import android.content.SharedPreferences
import com.example.dialectica.core.domain.SharedPrefsKeys.INIT_USER_KEY
import com.example.dialectica.core.domain.SharedPrefsKeys.TYPE_DATABASE_KEY
import com.example.dialectica.core.domain.SharedPrefsKeys.USER_NAME_KEY
import com.example.dialectica.core.domain.repositories.SharedPrefsRepository
import com.example.dialectica.utils.TYPE_ROOM
import com.example.dialectica.utils.USER_QUEST

class SharedPrefsRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SharedPrefsRepository {

    override fun setInitUser(init: Boolean) {
        sharedPrefs.edit().putBoolean(INIT_USER_KEY, init).apply()
    }

    override fun setUserName(name: String) {
        sharedPrefs.edit().putString(USER_NAME_KEY, name).apply()
    }

    override fun setTypeDatabase(type: String) {
        sharedPrefs.edit().putString(TYPE_DATABASE_KEY, type).apply()
    }

    override fun getInitUser() = sharedPrefs.getBoolean(INIT_USER_KEY, false)

    override fun getUserName() = sharedPrefs.getString(USER_NAME_KEY, USER_QUEST).toString()

    override fun getUserAuthorize() = sharedPrefs.getString(USER_NAME_KEY, USER_QUEST).toString() != USER_QUEST

    override fun getTypeDatabase() = sharedPrefs.getString(TYPE_DATABASE_KEY, TYPE_ROOM).toString()

}
