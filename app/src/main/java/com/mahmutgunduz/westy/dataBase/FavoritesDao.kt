package com.mahmutgunduz.westy.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.Observable
import androidx.room.OnConflictStrategy

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): Single<List<FavoritesData>>

    @Delete
    fun deleteFavorite(favorite: FavoritesData): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: FavoritesData): Completable
}