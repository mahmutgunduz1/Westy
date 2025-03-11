package com.mahmutgunduz.westy.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Single<List<FavoritesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoritesData): Completable

    @Query("DELETE FROM favorites WHERE productId = :id")
    fun deleteFavoriteById(id: Int): Completable

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE productId = :id LIMIT 1)")
    fun isFavorite(id: Int): Single<Boolean>
}