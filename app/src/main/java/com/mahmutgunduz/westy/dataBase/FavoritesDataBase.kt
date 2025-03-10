package com.mahmutgunduz.westy.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mahmutgunduz.westy.data.model.FavoritesData as DataModelFavoritesData
import com.mahmutgunduz.westy.dataBase.FavoritesData as DatabaseFavoritesData

// Include both entity classes in the database
@Database(
    entities = [
        DataModelFavoritesData::class,
        DatabaseFavoritesData::class
    ],
    version = 5
)
@TypeConverters(Converters::class)
abstract class FavoritesDataBase : RoomDatabase() {
    // This is the old DAO that uses the database package's FavoritesData
    abstract fun noteDao(): FavoritesDao
    
    // This method provides the data.dao.FavoritesDao implementation
    abstract fun favoritesDao(): com.mahmutgunduz.westy.data.dao.FavoritesDao

    companion object {
        private var instance: FavoritesDataBase? = null

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the favorites_old table
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS favorites_old (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        name TEXT NOT NULL,
                        price REAL NOT NULL,
                        imageUrl INTEGER NOT NULL
                    )
                    """
                )
                
                // Copy data from favorites to favorites_old if needed
                database.execSQL(
                    """
                    INSERT OR IGNORE INTO favorites_old (id, name, price, imageUrl)
                    SELECT id, title, price, CAST(image AS INTEGER)
                    FROM favorites
                    WHERE title IS NOT NULL
                    """
                )
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
                .addMigrations(MIGRATION_4_5)
                .build()
            }
            return instance!!
        }
    }
}