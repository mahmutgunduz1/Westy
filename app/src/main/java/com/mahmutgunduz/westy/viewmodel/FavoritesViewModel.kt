package com.mahmutgunduz.westy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutgunduz.westy.data.model.FavoritesData
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.data.dao.FavoritesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesDao: FavoritesDao
) : ViewModel() {
    
    val favorites: Flow<List<FavoritesData>> = favoritesDao.getAllFavorites()
    
    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            val favoriteData = FavoritesData.fromProduct(product)
            favoritesDao.insertFavorite(favoriteData)
        }
    }
    
    fun removeFromFavorites(product: Product) {
        viewModelScope.launch {
            val favoriteData = FavoritesData.fromProduct(product)
            favoritesDao.deleteFavorite(favoriteData)
        }
    }
    
    suspend fun isFavorite(productId: Int): Boolean {
        return favoritesDao.isFavorite(productId)
    }
} 