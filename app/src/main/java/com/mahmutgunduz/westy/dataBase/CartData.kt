package com.mahmutgunduz.westy.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmutgunduz.westy.data.model.Product

@Entity(tableName = "cart")
data class CartData(
    @PrimaryKey
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val productDescription: String,
    val quantity: Int = 1
) {
    companion object {
        fun fromProduct(product: Product, quantity: Int = 1): CartData {
            return CartData(
                productId = product.id,
                productName = product.title,
                productPrice = product.price,
                productImage = product.image,
                productDescription = product.description,
                quantity = quantity
            )
        }
    }
} 