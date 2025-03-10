package com.mahmutgunduz.westy.repository

import com.mahmutgunduz.westy.data.dao.FavoritesDao
import com.mahmutgunduz.westy.data.model.FavoritesData
import com.mahmutgunduz.westy.data.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepository @Inject constructor(
    private val favoritesDao: FavoritesDao
) {
    fun getAllFavorites(): Flow<List<FavoritesData>> {
        return favoritesDao.getAllFavorites()
    }
    
    suspend fun addToFavorites(product: Product) {
        val favoriteData = FavoritesData.fromProduct(product)
        favoritesDao.insertFavorite(favoriteData)
    }
    
    suspend fun removeFromFavorites(product: Product) {
        val favoriteData = FavoritesData.fromProduct(product)
        favoritesDao.deleteFavorite(favoriteData)
    }
    
    suspend fun isFavorite(productId: Int): Boolean {
        return favoritesDao.isFavorite(productId)
    }
} 