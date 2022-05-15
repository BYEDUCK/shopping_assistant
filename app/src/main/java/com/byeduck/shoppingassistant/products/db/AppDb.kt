package com.byeduck.shoppingassistant.products.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.byeduck.shoppingassistant.products.db.dao.SearchEntityDao

@Database(entities = [SearchEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDb : RoomDatabase() {

    abstract fun searchEntityDao(): SearchEntityDao

    companion object {

        private var dbInstance: AppDb? = null

        fun getDatabase(context: Context): AppDb {
            val tmpInstance = dbInstance
            if (tmpInstance != null) {
                return tmpInstance
            }
            val instance =
                Room.databaseBuilder(context.applicationContext, AppDb::class.java, "byeduck_sa_db")
                    .build()
            dbInstance = instance
            return instance
        }
    }
}