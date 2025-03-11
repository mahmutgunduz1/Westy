package com.mahmutgunduz.westy.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CartData::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CartDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao

    companion object {
        private var instance: CartDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): CartDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "cart_db"
                )
                .fallbackToDestructiveMigration()
                .build()
            }
            return instance!!
        }
    }
} 