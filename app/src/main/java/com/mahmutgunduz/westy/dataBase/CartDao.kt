package com.mahmutgunduz.westy.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Single<List<CartData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(cartItem: CartData): Completable

    @Query("DELETE FROM cart WHERE productId = :id")
    fun deleteCartItemById(id: Int): Completable

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE productId = :id LIMIT 1)")
    fun isInCart(id: Int): Single<Boolean>
    
    @Query("UPDATE cart SET quantity = quantity + 1 WHERE productId = :id")
    fun incrementQuantity(id: Int): Completable
    
    @Query("UPDATE cart SET quantity = quantity - 1 WHERE productId = :id AND quantity > 1")
    fun decrementQuantity(id: Int): Completable
    
    @Query("SELECT quantity FROM cart WHERE productId = :id")
    fun getQuantity(id: Int): Single<Int>
    
    @Query("DELETE FROM cart")
    fun clearCart(): Completable
} 