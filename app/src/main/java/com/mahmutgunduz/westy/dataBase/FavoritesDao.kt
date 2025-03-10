package com.mahmutgunduz.westy.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.mahmutgunduz.westy.data.model.FavoritesData as DataModelFavoritesData
import com.mahmutgunduz.westy.dataBase.FavoritesData as DatabaseFavoritesData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    // Methods for the database package's FavoritesData
    @Query("SELECT * FROM favorites_old")
    fun getAllFavorites(): Single<List<DatabaseFavoritesData>>

    @Delete
    fun deleteFavorite(favorite: DatabaseFavoritesData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: DatabaseFavoritesData): Completable

    // Coroutine versions for future use
    @Query("SELECT * FROM favorites_old")
    suspend fun getAllFavoritesCoroutine(): List<DatabaseFavoritesData>

    @Delete
    suspend fun deleteFavoriteCoroutine(favorite: DatabaseFavoritesData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoroutine(favorite: DatabaseFavoritesData)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites_old WHERE id = :itemId)")
    fun isFavorite(itemId: Int): Single<Boolean>
    
    // Methods to support the data.dao.FavoritesDao interface
    @Query("SELECT * FROM favorites")
    fun getAllFavoritesFlow(): Flow<List<DataModelFavoritesData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: DataModelFavoritesData)

    @Delete
    suspend fun deleteFavorite(favorite: DataModelFavoritesData)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id LIMIT 1)")
    suspend fun isFavoriteById(id: Int): Boolean
}