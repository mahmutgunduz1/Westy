package com.mahmutgunduz.westy.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavoritesData::class], version = 4)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun noteDao(): FavoritesDao

    companion object {
        private var instance: FavoritesDataBase? = null

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Migration logic here
                // Migrasyon mantığı buraya
            }
        }

        @Synchronized
        fun getDatabase(context: Context): FavoritesDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDataBase::class.java,
                    "favorites"
                )
                .fallbackToDestructiveMigration()
                .build()
            }
            return instance!!
        }
    }
}