package com.vicgcode.dialectica.core.data.repositories

import android.content.SharedPreferences
import com.vicgcode.dialectica.core.domain.SharedPrefsKeys.IS_AUTHORIZED_KEY
import com.vicgcode.dialectica.core.domain.SharedPrefsKeys.TYPE_DATABASE_KEY
import com.vicgcode.dialectica.core.domain.SharedPrefsKeys.USER_NAME_KEY
import com.vicgcode.dialectica.core.domain.repositories.SharedPrefsRepository
import com.vicgcode.dialectica.utils.TYPE_ROOM
import com.vicgcode.dialectica.utils.USER_QUEST

class SharedPrefsRepositoryImpl(
    private val sharedPrefs: SharedPreferences
): SharedPrefsRepository {

    override fun setAuthorize(init: Boolean) {
        sharedPrefs.edit().putBoolean(IS_AUTHORIZED_KEY, init).apply()
    }

    override fun setUserName(name: String?) {
        sharedPrefs.edit().putString(USER_NAME_KEY, name).apply()
    }

    override fun setTypeDatabase(type: String) {
        sharedPrefs.edit().putString(TYPE_DATABASE_KEY, type).apply()
    }

    override fun isAuthorize() = sharedPrefs.getBoolean(IS_AUTHORIZED_KEY, false)

    override fun getUserName() = sharedPrefs.getString(USER_NAME_KEY, USER_QUEST).toString()

    override fun getUserAuthorize() = sharedPrefs.getString(USER_NAME_KEY, USER_QUEST).toString() != USER_QUEST

    override fun getTypeDatabase() = sharedPrefs.getString(TYPE_DATABASE_KEY, TYPE_ROOM).toString()

    override fun setBoolean(key: String, value: Boolean) = sharedPrefs.edit().putBoolean(key, value).apply()

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = sharedPrefs.getBoolean(key, defaultValue)
}
