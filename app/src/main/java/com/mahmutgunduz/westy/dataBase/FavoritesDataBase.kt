package com.mahmutgunduz.westy.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritesData::class], version = 3)
abstract class FavoritesDataBase : RoomDatabase() {
    abstract fun noteDao(): FavoritesDao

    companion object {
        private var instance: FavoritesDataBase? = null

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