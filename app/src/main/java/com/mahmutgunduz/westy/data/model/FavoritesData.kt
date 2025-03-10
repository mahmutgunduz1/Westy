package com.mahmutgunduz.westy.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mahmutgunduz.westy.dataBase.Converters

@Entity(tableName = "favorites")
data class FavoritesData(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    @TypeConverters(Converters::class)
    val rating: Rating? = null
) {
    companion object {
        fun fromProduct(product: Product): FavoritesData {
            return FavoritesData(
                id = product.id,
                title = product.title,
                price = product.price,
                description = product.description,
                category = product.category,
                image = product.image,
                rating = product.rating
            )
        }
    }
} 