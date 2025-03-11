package com.mahmutgunduz.westy.data

import com.mahmutgunduz.westy.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ShoppingApi {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
    
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(@Path("category") category: String): List<Product>
}