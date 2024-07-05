package com.vicgcode.dialectica.data.models.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class QuestionConverter {

    @TypeConverter
    fun storedStringToQuestionList(data: String?): List<DialectQuestion>? {
        val gson = Gson()
        if (data == null) {
            return Collections.emptyList()
        }
        val turnsType = object : TypeToken<List<DialectQuestion>>() {}.type
        return gson.fromJson(data, turnsType)
    }

    @TypeConverter
    fun questionListToStoredString(interests: List<DialectQuestion?>?): String? {
        return Gson().toJson(interests)
    }
}
