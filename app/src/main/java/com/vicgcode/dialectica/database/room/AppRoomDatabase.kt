package com.vicgcode.dialectica.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vicgcode.dialectica.data.models.entity.DataConverter
import com.vicgcode.dialectica.data.models.entity.DialectInterest
import com.vicgcode.dialectica.data.models.entity.DialectPerson
import com.vicgcode.dialectica.data.models.entity.DialectQuestion
import com.vicgcode.dialectica.data.models.entity.QuestionConverter

@Database(entities = [DialectQuestion::class, DialectInterest::class, DialectPerson::class],version = 1)
@TypeConverters(DataConverter::class, QuestionConverter::class)
abstract class AppRoomDatabase: RoomDatabase() {
    abstract fun getAppRoomDao(): AppRoomDao

    companion object {

        @Volatile
        private var database: AppRoomDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppRoomDatabase{
            return if (database == null){
                database = Room.databaseBuilder(
                    context,
                    AppRoomDatabase::class.java,
                    "database"
                ).build()
                database as AppRoomDatabase
            } else database as AppRoomDatabase
        }
    }
}
