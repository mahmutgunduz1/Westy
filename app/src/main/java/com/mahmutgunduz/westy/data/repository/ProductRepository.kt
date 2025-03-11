package com.mahmutgunduz.westy.data.repository

import com.mahmutgunduz.westy.data.ShoppingApi
import com.mahmutgunduz.westy.data.model.Product
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val api: ShoppingApi
) {
    suspend fun getProducts(): List<Product> {
        return api.getProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return api.getProductById(id)
    }
    
    suspend fun getProductsByCategory(category: String): List<Product> {
        return api.getProductsByCategory(category)
    }
} 