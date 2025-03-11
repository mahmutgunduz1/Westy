package com.mahmutgunduz.westy.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FavoritesData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        private var instance: FavoritesDataBase? = null

        @Synchronized
        fun getDatabase(context: Context): FavoritesDataBase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDataBase::class.java,
                    "favorites_db"
                )
                .fallbackToDestructiveMigration()
                .build()
            }
            return instance!!
        }
    }
}