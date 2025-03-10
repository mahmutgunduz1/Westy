package com.mahmutgunduz.westy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutgunduz.westy.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Ürünlerle ilgili iş mantığını yöneten ViewModel sınıfı
// UI ile veri katmanı arasında köprü görevi görür
@HiltViewModel
class ProductViewModel @Inject constructor() : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadProducts()
    }

    private fun loadProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // This is a placeholder. In a real app, you would fetch products from a repository
                val dummyProducts = createDummyProducts()
                _products.value = dummyProducts
            } catch (e: Exception) {
                // Handle error
                _error.value = e.message
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Temporary function to create dummy products for testing
    private fun createDummyProducts(): List<Product> {
        return listOf(
            Product(
                id = 1,
                title = "Fjallraven - Foldsack No. 1 Backpack",
                price = 109.95,
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                rating = null
            ),
            Product(
                id = 2,
                title = "Mens Casual Premium Slim Fit T-Shirts",
                price = 22.3,
                description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing.",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                rating = null
            ),
            Product(
                id = 3,
                title = "Mens Cotton Jacket",
                price = 55.99,
                description = "Great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors.",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                rating = null
            ),
            Product(
                id = 4,
                title = "Womens T-Shirt",
                price = 39.99,
                description = "The color could be slightly different between on the screen and in practice.",
                category = "women's clothing",
                image = "https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg",
                rating = null
            )
        )
    }
} 