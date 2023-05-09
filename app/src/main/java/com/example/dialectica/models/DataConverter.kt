package com.example.dialectica.models

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class DataConverter {
    @TypeConverter
    fun storedStringToInterestList(data: String?): List<String>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val turnsType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, turnsType)
    }

    @TypeConverter
    fun interestListToStoredString(interests: List<String?>?): String? {
        return Gson().toJson(interests)
    }
}
