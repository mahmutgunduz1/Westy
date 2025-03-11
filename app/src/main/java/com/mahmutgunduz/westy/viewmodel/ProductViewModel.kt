package com.mahmutgunduz.westy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahmutgunduz.westy.data.model.Product
import com.mahmutgunduz.westy.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Ürünlerle ilgili iş mantığını yöneten ViewModel sınıfı
// UI ile veri katmanı arasında köprü görevi görür
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadProducts()
    }

    fun loadProducts() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val productList = repository.getProducts()
                _products.value = productList
            } catch (e: Exception) {
                // Handle error
                _error.value = e.message
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductsByCategory(category: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val productList = repository.getProductsByCategory(category)
                _products.value = productList
            } catch (e: Exception) {
                // Handle error
                _error.value = e.message
                _products.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadProductById(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val productDetails = repository.getProductById(id)
                _product.value = productDetails
            } catch (e: Exception) {
                // Handle error
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Kategori adını formatla (API'nin beklediği formata dönüştür)
    fun formatCategoryName(categoryId: Int): String {
        return when (categoryId) {
            0 -> "men's clothing" // Üst Giyim
            1 -> "women's clothing" // Alt Giyim
            2 -> "jewelery" // Ayakkabılar (API'de ayakkabı kategorisi olmadığı için jewelery kullanıldı)
            3 -> "electronics" // Aksesuarlar (API'de aksesuar kategorisi olmadığı için electronics kullanıldı)
            else -> "" // Diğer kategoriler için boş string döndür, tüm ürünleri getirecek
        }
    }
} 