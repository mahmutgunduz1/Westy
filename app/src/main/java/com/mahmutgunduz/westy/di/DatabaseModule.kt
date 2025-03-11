package com.mahmutgunduz.westy.di

import android.content.Context
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): FavoritesDataBase {
        return FavoritesDataBase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: FavoritesDataBase): FavoritesDao {
        return database.favoritesDao()
    }
} 