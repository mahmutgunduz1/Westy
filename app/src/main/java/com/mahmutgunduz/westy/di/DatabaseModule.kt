package com.mahmutgunduz.westy.di

import android.content.Context
import com.mahmutgunduz.westy.dataBase.CartDao
import com.mahmutgunduz.westy.dataBase.CartDatabase
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
    fun provideFavoritesDatabase(@ApplicationContext context: Context): FavoritesDataBase {
        return FavoritesDataBase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: FavoritesDataBase): FavoritesDao {
        return database.favoritesDao()
    }
    
    @Provides
    @Singleton
    fun provideCartDatabase(@ApplicationContext context: Context): CartDatabase {
        return CartDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCartDao(database: CartDatabase): CartDao {
        return database.cartDao()
    }
} 