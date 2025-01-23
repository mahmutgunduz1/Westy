package com.mahmutgunduz.westy.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Single<List<FavoritesData>>

    @Delete
    fun deleteFavorite(favorite: FavoritesData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoritesData): Completable

    // Coroutine versions for future use
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavoritesCoroutine(): List<FavoritesData>

    @Delete
    suspend fun deleteFavoriteCoroutine(favorite: FavoritesData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoroutine(favorite: FavoritesData)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :itemId)")
    fun isFavorite(itemId: Int): Single<Boolean>
}