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





} 