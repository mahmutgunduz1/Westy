package com.mahmutgunduz.westy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.dataBase.FavoritesDao
import com.mahmutgunduz.westy.dataBase.FavoritesData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesDao: FavoritesDao
) : ViewModel() {
    
    val favorites: LiveData<List<FavoritesData>> = MutableLiveData<List<FavoritesData>>().apply {
        favoritesDao.getAllFavorites()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ favoritesList ->
                this.value = favoritesList
            }, { error ->
                // Handle error
            })
    }
    
    fun addToFavorites(product: Product) {
        viewModelScope.launch {
            val favoriteData = FavoritesData.fromProduct(product)
            favoritesDao.insertFavorite(favoriteData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }
    
    fun removeFromFavorites(product: Product) {
        viewModelScope.launch {
            val favoriteData = FavoritesData.fromProduct(product)
            favoritesDao.deleteFavoriteById(favoriteData.productId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        }
    }
    
    suspend fun isFavorite(productId: Int): Boolean {
        return favoritesDao.isFavorite(productId)
            .subscribeOn(Schedulers.io())
            .blockingGet()
    }
} 