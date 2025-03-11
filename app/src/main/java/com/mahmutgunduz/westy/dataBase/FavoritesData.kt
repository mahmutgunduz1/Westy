package com.mahmutgunduz.westy.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mahmutgunduz.westy.data.model.Product

@Entity(tableName = "favorites")
data class FavoritesData(
    @PrimaryKey
    val productId: Int,
    val productName: String,
    val productPrice: Double,
    val productImage: String,
    val productDescription: String
) {
    companion object {
        fun fromProduct(product: Product): FavoritesData {
            return FavoritesData(
                productId = product.id,
                productName = product.title,
                productPrice = product.price,
                productImage = product.image,
                productDescription = product.description
            )
        }
    }
}
